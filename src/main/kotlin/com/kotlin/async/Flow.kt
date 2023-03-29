package com.kotlin.async

fun main() {
    show()
}

fun show() {
    //listar().forEach { println(it }
    secuencia().forEach { println(it) }
}

fun listar(): List<Int> = listOf(3, 78, 90)

fun secuencia(): Sequence<Int> = sequence {
    for(i in 1..3) {
        Thread.sleep(1000)
        yield(i)
    }
}