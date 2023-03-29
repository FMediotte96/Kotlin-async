package com.kotlin.async

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {
    //blockingExample()
    //suspendExample()
    //suspendExample2()
    //dispatcher()
    //launch()
    //jobExample()
    //Thread.sleep(5000)
    //asyncAwait()
    //asyncAwaitDeferred()
    println(measureTimeMillis { asyncAwait() }.toString())
    println(measureTimeMillis { asyncAwaitDeferred() }.toString())
}

fun longTaskWithMessage(message: String) {
    Thread.sleep(4000)
    println(message + Thread.currentThread().name)
}

fun blockingExample() {
    println("Tarea1 " + Thread.currentThread().name)
    //longTaskWithMessage("Tarea2 ")
    //delayCoroutine("Tarea2")
    println("Tarea3 " + Thread.currentThread().name)
}

suspend fun delayCoroutine(message: String) {
    delay(timeMillis = 4000)
    println(message + Thread.currentThread().name)
}

//runBlocking: nos bloquea el hilo y nos ejecuta el código en orden
fun suspendExample() {
    println("Tarea1 " + Thread.currentThread().name)
    runBlocking {
        delayCoroutine("Tarea2 ")
    }
    println("Tarea3 " + Thread.currentThread().name)
}

fun suspendExample2() = runBlocking{
    println("Tarea1 " + Thread.currentThread().name)
    delayCoroutine("Tarea2 ")
    println("Tarea3 " + Thread.currentThread().name)
}

//dispatcher: indica en que hilo se ejecuta una coroutine, si no indicamos ningún dispatcher, se ejecuta en el hilo actual.
@OptIn(DelicateCoroutinesApi::class)
fun dispatcher() {
    runBlocking {
        println("Hilo en el que se ejecuta 1: ${Thread.currentThread().name}")
    }
    runBlocking(Dispatchers.Unconfined) {
        println("Hilo en el que se ejecuta 2: ${Thread.currentThread().name}")
    }
    runBlocking(Dispatchers.Default) {//uso intensivo de la cpu
        println("Hilo en el que se ejecuta 3: ${Thread.currentThread().name}")
    }
    runBlocking(Dispatchers.IO) {//entrada/salida de datos
        println("Hilo en el que se ejecuta 4: ${Thread.currentThread().name}")
    }
    runBlocking(newSingleThreadContext("MyThread")) {
        println("Hilo en el que se ejecuta 5: ${Thread.currentThread().name}")
    }
//    runBlocking(Dispatchers.Main) {//Para trabajar con Android
//        println("Hilo en el que se ejecuta 6: ${Thread.currentThread().name}")
//    }
}

//launch: lanza una nueva coroutine sin bloquear el hilo actual y nos devuelve una referencia como job.
@OptIn(DelicateCoroutinesApi::class)
fun launch() {
    println("Tarea1 " + Thread.currentThread().name)
    GlobalScope.launch {
        delayCoroutine("Tarea2 ")
    }
    println("Tarea3 " + Thread.currentThread().name)
}

//Scope: nos define el ciclo de vida de una coroutine. La misma siempre tiene que tener asociado un scope.
//GlobalScope: esta asociado a la vida útil de la aplicación. No se suele recomendar su uso.

//Job: elemento cancelable con un ciclo de vida que culmina a su finalización. Se puede cancelar y finalizar su ejecución. Launch nos va a devolver un job.
fun jobExample() {
    println("Tarea1 " +  Thread.currentThread().name)
    val job = GlobalScope.launch {
        delayCoroutine("Tarea2 ")
    }
    println("Tarea3 " + Thread.currentThread().name)
    job.cancel()
}

suspend fun calculateHard(): Int {
    delay(2000)
    return 15
}

//Async: ejecutamos algo y esperamos su resultado. Async nos va a devolver un deferred. Nos permite escribir código asincrono
//como si fuese sincrono.
fun asyncAwait() = runBlocking {
    val number1: Int = async { calculateHard() }.await()
    val number2: Int = async { calculateHard() }.await()
    val result = number1 + number2
    println(result.toString())
}

//Deferred: async nos devuelve un futuro cancelable sin bloqueos.
fun asyncAwaitDeferred() = runBlocking {
    val number1: Deferred<Int> = async { calculateHard() }
    val number2: Deferred<Int> = async { calculateHard() }
    val result: Int = number1.await() + number2.await()
    println(result.toString())
}