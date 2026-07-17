package com.sumino.toastly.state

import com.sumino.toastly.model.ToastConfig
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ToastStateTest {

    private fun config(message: String) = ToastConfig(message = message)

    @Test
    fun push_trims_to_maxStack_keeping_newest() {
        val state = ToastState(maxStack = 2)

        state.push(config("a"))
        state.push(config("b"))
        state.push(config("c"))

        assertEquals(listOf("b", "c"), state.stack.map { it.config.message })
    }

    @Test
    fun dismiss_removes_only_the_matching_entry() {
        val state = ToastState(maxStack = 3)
        state.push(config("a"))
        state.push(config("b"))

        val idToRemove = state.stack.first { it.config.message == "a" }.id
        state.dismiss(idToRemove)

        assertEquals(listOf("b"), state.stack.map { it.config.message })
    }

    @Test
    fun dismissAll_empties_the_stack() {
        val state = ToastState()

        state.push(config("a"))
        state.push(config("b"))
        state.dismissAll()

        assertTrue(state.stack.isEmpty())
    }
}
