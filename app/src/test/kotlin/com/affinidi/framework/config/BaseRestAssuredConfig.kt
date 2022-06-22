package com.affinidi.framework.config

import io.qameta.allure.restassured.AllureRestAssured
import io.restassured.RestAssured
import io.restassured.config.DecoderConfig
import io.restassured.config.EncoderConfig
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.http.ContentType
import io.restassured.specification.PreemptiveAuthSpec
import org.springframework.http.HttpHeaders
import java.nio.charset.StandardCharsets


object BaseRestAssuredConfig {
    private val PREEMPTIVE_AUTH_SPEC: PreemptiveAuthSpec

    fun authSpec(token: String, baseUri: String) = PREEMPTIVE_AUTH_SPEC.oauth2(token).baseUri(baseUri)!!

    init {
        val config = RestAssured.config()
            .encoderConfig(
                EncoderConfig().defaultContentCharset(StandardCharsets.UTF_8)
                    .appendDefaultContentCharsetToContentTypeIfUndefined(false)
            )

        PREEMPTIVE_AUTH_SPEC = RestAssured.given()
            .config(config)
            .filters(RequestLoggingFilter(), ResponseLoggingFilter(), AllureRestAssured())
            .header(HttpHeaders.CONTENT_TYPE, ContentType.JSON.toString() + "; charset=UTF-8")
            .relaxedHTTPSValidation()
            .auth()
            .preemptive()
    }
}
