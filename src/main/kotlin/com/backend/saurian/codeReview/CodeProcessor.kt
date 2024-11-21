package com.backend.saurian.codeReview

import kotlinx.coroutines.*
import kotlin.random.Random

class DataProcessor {

    private val data = listOf(1, 2, 3, 4, 5)

    fun processData() {
        CoroutineScope(Dispatchers.Default).launch {
            data.map { value ->
                async {
                    processValue(value) { result ->
                        if (result > 10) {
                            println("High value: $result")
                        } else {
                            launch(Dispatchers.IO) {
                                saveResult(result) { success ->
                                    if (success) {
                                        println("Result $result saved successfully")
                                    } else {
                                        println("Failed to save result $result")
                                    }
                                }
                            }
                        }
                    }
                }
            }.forEach { it.await() }
        }
    }

    private suspend fun processValue(value: Int, callback: (Int) -> Unit) {
        delay(100) // Simulate processing
        callback(value * Random.nextInt(1, 5))
    }

    private suspend fun saveResult(result: Int, callback: (Boolean) -> Unit) {
        delay(50) // Simulate saving
        callback(result % 2 == 0) // Simulate save success based on result parity
    }
}

