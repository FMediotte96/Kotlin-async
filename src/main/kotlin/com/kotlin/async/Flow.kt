package com.kotlin.async

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

@OptIn(FlowPreview::class)
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

//    runBlocking {
//        //secondFlow().collect { println(it) }
//        thirdFlow().collect { println(it) }
//    }

    //map
//    runBlocking {
//        (1..3).asFlow()
//            .map { performRequest(it) }
//            .collect { println(it) }
//    }

    //filter
//    runBlocking {
//        (1..3).asFlow()
//            .filter { it > 1 }
//            .map { performRequest(it) }
//            .collect { println(it) }
//    }

    //transform:
    /*runBlocking {
        (1..3).asFlow()
            .transform {
                emit("Making request $it")
                emit(performRequest(it))
            }
            .collect { println(it) }
    }*/

    //take: cancela el flow cuando llega al límite que le indicamos
//    runBlocking {
//        (1..3).asFlow()
//            .take(2)
//            .collect { println(it) }
//    }

    //list:
//    runBlocking {
//        val list: List<Int> = (1..3).asFlow().toList()
//        println(list)
//    }

    //first: devuelve el primer elemento del flow
//    runBlocking {
//        val number = (6..90).asFlow().first()
//        println(number)
//    }

    //reduce:
//    runBlocking {
//        val result = (1..3).asFlow().reduce { acc, value -> acc + value }
//        println(result)
//    }

    //Secuencial
//    runBlocking {
//        (1..5).asFlow()
//            .filter {
//                println("Filtrado $it")
//                it % 2 == 0
//            }
//            .map {
//                println("Map $it")
//                "String $it"
//            }
//            .collect { println("Collect $it") }
//    }

    //los va guardando un buffer
//    runBlocking {
//        val time = measureTimeMillis {
//            firstFlow()
//                .buffer()
//                .collect {
//                delay(300)
//                println(it)
//            }
//        }
//        println("$time ms")
//    }

    //conflate:
//    runBlocking {
//        val time = measureTimeMillis {
//            firstFlow()
//                .conflate()
//                .collect {
//                delay(300)
//                println(it)
//            }
//        }
//        println("$time ms")
//    }

    //CollectLatest
//    runBlocking {
//        val time = measureTimeMillis {
//            firstFlow()
//                .collectLatest {
//                    println("Collecting $it")
//                    delay(300)
//                    println("Finalizado $it")
//                }
//        }
//        println("$time ms")
//    }

    //Zip: combina los valores correspondientes de 2 flujos
//    val numbers = (1..3).asFlow()
//    val strings = flowOf("Uno", "Dos", "Tres")
//
//    runBlocking {
//        numbers.zip(strings) {
//            a,b -> "Zip: $a -> $b"
//        }.collect{ println(it) }
//    }

    //FlatMapConcat
//    runBlocking {
//        val startTime = System.currentTimeMillis()
//        (1..3).asFlow().onEach { delay(100) }
//            .flatMapConcat { requestFlow(it) }
//            .collect { println("$it at ${System.currentTimeMillis() - startTime} ms from start") }
//    }

    //FlatMapMerge
    runBlocking {
        val startTime = System.currentTimeMillis()
        (1..3).asFlow().onEach { delay(100) }
            .flatMapMerge { requestFlow(it) }
            .collect { println("$it at ${System.currentTimeMillis() - startTime} ms from start") }
    }
}

fun requestFlow(i: Int): Flow<String> = flow {
    emit("$i: First")
    delay(500)
    emit("$i: Second")
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
        delay(100)
        emit(i)
    }
}

fun secondFlow(): Flow<Int> {
    return flowOf(1, 2, 3)
}

fun thirdFlow(): Flow<Int> {
    return (1..3).asFlow()
}

//Los flows se pueden transformar
suspend fun performRequest(request: Int): String {
    delay(1000)
    return "response $request"
}