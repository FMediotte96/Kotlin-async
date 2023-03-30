package com.kotlin.async

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

fun main() {
    /*//show()
    runBlocking {
        runAsynchronous().forEach { println(it) }
    }*/

    /*runBlocking {
        launch {
            for (j in 1..3) {
                println("No estoy bloqueado $j")
                delay(1000)
            }
        }
        firstFlow().collect { println(it) }
    }

    runBlocking {
        println("Llamando Flow...")
        val flow = firstFlow();
        println("Collect...")
        flow.collect { println(it) }
        println("Collect again...")
        flow.collect { println(it) }
    }
    //Cancel flow
    runBlocking {
        withTimeoutOrNull(2500) {
            firstFlow().collect { println(it) }
        }
        println("Finalizado")
    }
    */

    runBlocking {
        secondFlow().collect { println(it) }
    }
}

fun show() {
    //listar().forEach { println(it }
    secuencia().forEach { println(it) }
}

fun listar(): List<Int> = listOf(3, 78, 90)

fun secuencia(): Sequence<Int> = sequence {
    for (i in 1..3) {
        Thread.sleep(1000)
        yield(i)
    }
}

suspend fun runAsynchronous(): List<Int> {
    return runBlocking {
        delay(1000)
        return@runBlocking listOf(1, 2, 3)
    }
}

//Flow: call asynchronous data stream, se ejecuta de manera sequencial en la misma coroutine. Emite valores secuencialmente.
//Parecidos a los streams de Java, no nos tenemos que preocupar por los memory leaks.
//Necesita siempre un constructor

fun firstFlow(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(1000)
        emit(i)
    }
}

fun secondFlow(): Flow<Int> {
    return flowOf(1, 2, 3)
}