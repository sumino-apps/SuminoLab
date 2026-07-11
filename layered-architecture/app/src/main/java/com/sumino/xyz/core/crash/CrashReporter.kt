package com.sumino.xyz.core.crash

interface CrashReporter {
    fun report(throwable: Throwable)
}