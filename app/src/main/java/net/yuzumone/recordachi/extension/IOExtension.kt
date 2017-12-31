package net.yuzumone.recordachi.extension

import java.util.concurrent.Executors

fun ioThread(f : () -> Unit) {
    Executors.newSingleThreadExecutor().execute(f)
}