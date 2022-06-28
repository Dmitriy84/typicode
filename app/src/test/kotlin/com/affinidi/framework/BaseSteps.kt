package com.affinidi.framework

import com.affinidi.api.data.responses.RestAssuredSpecification
import com.affinidi.framework.config.ApplicationProps
import com.affinidi.framework.config.BaseRestAssuredConfig
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.qameta.allure.Step
import io.restassured.http.Method
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import io.restassured.specification.ResponseSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.Base64
import java.util.UUID


@Component
abstract class BaseSteps {
    abstract fun getRequestSpec(): RequestSpecification
    protected fun requestBase(path: String) =
        BaseRestAssuredConfig.authSpec("", applicationProps.baseURI).basePath(path)!!

    final inline fun <reified T> request(
        path: String = "",
        method: Method = Method.GET,
        specification: ResponseSpecification = RestAssuredSpecification.OK_RESPONSE_SPEC.value,
        parametersMap: Map<String, Any> = emptyMap(),
        headers: Map<String, Any> = emptyMap(),
        body: Any? = null,
    ): Pair<T, Response> {
        val response = getRequestSpec()
            .headers(headers)
            .body(body ?: "")
            .queryParams(parametersMap)
            .request(method, path)
        return response
            .then()
            .spec(specification)
            .extract()
            .`as`(T::class.java)!! to response!!
    }

    @Autowired
    lateinit var applicationProps: ApplicationProps
}
