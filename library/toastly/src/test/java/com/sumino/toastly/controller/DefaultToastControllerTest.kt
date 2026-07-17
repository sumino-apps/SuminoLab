package com.sumino.toastly.controller

import com.sumino.toastly.model.ToastConfig
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DefaultToastControllerTest {

    private fun config(message: String) = ToastConfig(message = message)

    @Test
    fun show_enqueues_and_poll_returns_in_fifo_order() {
        val controller = DefaultToastController()

        controller.show(config("a"))
        controller.show(config("b"))

        assertEquals("a", controller.poll()?.message)
        assertEquals("b", controller.poll()?.message)
        assertNull(controller.poll())
    }

    @Test
    fun only_the_most_recently_activated_observer_is_active() {
        val controller = DefaultToastController()

        assertFalse(controller.isActive("first"))

        controller.setActive("first")
        assertTrue(controller.isActive("first"))

        controller.setActive("second")
        assertFalse(controller.isActive("first"))
        assertTrue(controller.isActive("second"))

        // Deactivating a non-active observer is a no-op.
        controller.setInactive("first")
        assertTrue(controller.isActive("second"))

        controller.setInactive("second")
        assertFalse(controller.isActive("second"))
    }

    @Test
    fun queue_is_bounded_and_drops_the_oldest() {
        val controller = DefaultToastController()

        repeat(40) { controller.show(config(it.toString())) }
        val drained = generateSequence { controller.poll() }.toList()

        // MAX_QUEUE_SIZE caps retained toasts; the eight oldest (0..7) were evicted.
        assertEquals(32, drained.size)
        assertEquals("8", drained.first().message)
        assertEquals("39", drained.last().message)
    }

    @Test
    fun dismissAll_clears_pending_queue() {
        val controller = DefaultToastController()

        controller.show(config("a"))
        controller.dismissAll()

        assertNull(controller.poll())
    }
}
