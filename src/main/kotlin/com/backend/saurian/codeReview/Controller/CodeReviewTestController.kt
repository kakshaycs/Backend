package com.backend.saurian.codeReview.Controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus

@RestController
@RequestMapping("/api/code-review/test")
class CodeReviewTestController {

    @GetMapping("/health")
    fun healthCheck(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(mapOf(
            "status" to "UP",
            "message" to "Code Review Test Controller is running"
        ))
    }

    @PostMapping("/echo")
    fun echoTest(@RequestBody request: TestRequest): ResponseEntity<TestResponse> {
        return ResponseEntity.ok(TestResponse(
            message = "Received: ${request.message}",
            timestamp = System.currentTimeMillis(),
            status = "SUCCESS"
        ))
    }

    @GetMapping("/error-simulation")
    fun errorTest(@RequestParam throwError: Boolean = false): ResponseEntity<Any> {
        return if (throwError) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("error" to "Simulated error for testing"))
        } else {
            ResponseEntity.ok(mapOf("message" to "No error simulation"))
        }
    }

    @GetMapping("/parameters")
    fun parameterTest(
        @RequestParam(required = false) stringParam: String?,
        @RequestParam(required = false) intParam: Int?,
        @RequestParam(required = false) boolParam: Boolean?
    ): ResponseEntity<Map<String, Any?>> {
        return ResponseEntity.ok(mapOf(
            "stringParam" to stringParam,
            "intParam" to intParam,
            "boolParam" to boolParam,
            "timestamp" to System.currentTimeMillis()
        ))
    }
}

data class TestRequest(
    val message: String,
    val metadata: Map<String, Any>? = null
)

data class TestResponse(
    val message: String,
    val timestamp: Long,
    val status: String
)
