package com.sumino.xyz.core.architecture

/**
 * Base interface for UI State.
 * Represents the current state of a Screen/Feature.
 */
interface ViewState

/**
 * Base interface for UI Events.
 * Represents user actions or system events that trigger a change.
 */
interface ViewEvent

/**
 * Base interface for UI Side Effects.
 * Represents one-off events like Toasts, Navigation, SnackBar that shouldn't be re-triggered on recomposition.
 */
interface ViewSideEffect
