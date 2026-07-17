package com.sumino.toastly.controller

import com.sumino.toastly.model.ToastConfig
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Default [ToastController] implementation.
 *
 * Producers enqueue toasts through [show]; the queue buffers them until a
 * [com.sumino.toastly.ui.ToastObserver] is active (its host is resumed) and drains it.
 * Buffering is what lets a toast requested during a screen transition — when no observer is
 * momentarily active — still appear once one resumes.
 *
 * The class is deliberately free of any Android UI or DI dependency, so it can be constructed
 * directly in a unit test and driven without a device. All public members are thread-safe.
 *
 * Most apps use the process-wide instance exposed as [com.sumino.toastly.Toastly]; create your
 * own instance only when you want an isolated scope (for example, per-feature or in a test).
 */
class DefaultToastController : ToastController {

    // Pending toasts waiting to be drained by the active observer.
    private val queue = ConcurrentLinkedQueue<ToastConfig>()

    // Wakes the observer whenever work is enqueued. replay = 1 re-delivers the latest wake-up
    // to an observer that subscribes slightly after show() was called.
    private val _signal = MutableSharedFlow<Unit>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    internal val signal: SharedFlow<Unit> = _signal.asSharedFlow()

    // Emitted by dismissAll() so the observer can clear the on-screen stack.
    private val _dismissAllSignal = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    internal val dismissAllSignal: SharedFlow<Unit> = _dismissAllSignal.asSharedFlow()

    // Id of the observer currently allowed to drain the queue (the foreground one). Guards
    // against two observers double-showing the same toast in a multi-Activity app.
    @Volatile
    private var activeObserverId: String? = null

    override fun show(config: ToastConfig) {
        // Bound the queue so config lambdas (which may capture UI references) can't accumulate
        // without limit while no observer is active — a memory-leak guard.
        while (queue.size >= MAX_QUEUE_SIZE) queue.poll()
        queue.add(config)
        _signal.tryEmit(Unit)
    }

    override fun dismissAll() {
        queue.clear()
        _dismissAllSignal.tryEmit(Unit)
    }

    /** `true` if [observerId] is the observer currently permitted to drain the queue. */
    internal fun isActive(observerId: String): Boolean = activeObserverId == observerId

    /** Marks [observerId] active (call on ON_RESUME) and replays any pending work to it. */
    internal fun setActive(observerId: String) {
        activeObserverId = observerId
        if (queue.isNotEmpty()) _signal.tryEmit(Unit)
    }

    /** Marks [observerId] inactive (call on ON_PAUSE / dispose) if it is the active one. */
    internal fun setInactive(observerId: String) {
        if (activeObserverId == observerId) activeObserverId = null
    }

    /** Removes and returns the next pending toast, or `null` if the queue is empty. */
    internal fun poll(): ToastConfig? = queue.poll()

    private companion object {
        // Upper bound on undrained toasts; oldest are dropped past this.
        const val MAX_QUEUE_SIZE = 32
    }
}
