package com.affinidi.framework

import com.affinidi.api.steps.responses.RestAssuredSpecification
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


//@EnableConfigurationProperties(ApplicationProps::class)
//@TestPropertySource("classpath:profiles/application.properties")
@Component
abstract class BaseSteps {
    abstract fun getRequestSpec(): RequestSpecification
    protected fun requestBase(path: String) =
        BaseRestAssuredConfig.authSpec("", applicationProps.baseURI).basePath(path)!!

    final inline fun <reified T> request(
        path: String = "",
        method: Method = Method.GET,
        specification: ResponseSpecification = RestAssuredSpecification.OK_RESPONSE_SPEC.value,
        parametersMap: Map<String, Any> = emptyMap()
    ): Pair<T, Response> {
        val response = getRequestSpec()
            .queryParams(parametersMap)
            .request(method, path)
        return response
            .then()
            .spec(specification)
            .extract()
            .`as`(T::class.java)!! to response!!
    }

    @Autowired
    protected lateinit var applicationProps: ApplicationProps

    @Step("first step")
    fun test() {
        println("baseURI=${applicationProps.baseURI}")
    }

//    protected fun requestBase(path: String) = BaseRestAssuredConfig.authSpec(apiToken1, baseURI).basePath(path)!!

    fun getUserID(token: String = "") =
        UUID.fromString(
            (getFromToken(
                "https://api.skedulo.com/ven",
                token
            ) as LinkedHashMap<*, *>)["user_id"] as String?
        )!!

    fun getTenantID() = getFromToken("https://api.skedulo.com/organization_id") as String

    fun getInternalAdminUserID() = getUserID()

    private fun getFromToken(key: String, token: String = "") =
        ObjectMapper().readValue(
            Base64.getDecoder().decode(token.split(".").toTypedArray()[1]),
            object : TypeReference<Map<String?, Any?>?>() {}
        )?.get(key)
}
