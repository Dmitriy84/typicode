package com.affinidi.api.steps.responses

import io.restassured.builder.ResponseSpecBuilder
import io.restassured.http.ContentType
import io.restassured.specification.ResponseSpecification
import org.apache.http.HttpStatus

enum class RestAssuredSpecification(val value: ResponseSpecification) {
    CREATED_RESPONSE_SPEC(
        ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectStatusCode(HttpStatus.SC_CREATED)
            .expectHeaders(
                mapOf(
                    "access-control-allow-credentials" to "true",
                    "Connection" to "keep-alive",
                    "Pragma" to "no-cache"
                )
            )
            .build()
    ),
    OK_RESPONSE_SPEC(
        ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectStatusCode(HttpStatus.SC_OK)
            .expectHeaders(
                mapOf(
                    "access-control-allow-credentials" to "true",
                    "Connection" to "keep-alive",
                    "Pragma" to "no-cache",
                    "Content-Encoding" to "gzip"
                )
            )
            .build()
    )
}
