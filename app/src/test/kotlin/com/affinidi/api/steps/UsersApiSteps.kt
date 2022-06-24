package com.affinidi.api.steps

import com.affinidi.api.data.request.users.PostUsersRequest
import com.affinidi.api.data.responses.RestAssuredSpecification
import com.affinidi.api.data.responses.users.GetUsersResponse
import com.affinidi.api.data.responses.users.PostUsersResponse
import com.affinidi.framework.BaseSteps
import io.qameta.allure.Step
import io.restassured.http.Method
import org.springframework.stereotype.Component


@Component
class UsersApiSteps : BaseSteps() {
    override fun getRequestSpec() = requestBase("users")

    @Step("GET: /users")
    fun getUsers() = request<Array<GetUsersResponse>>()

    @Step("POST: /users")
    fun postUsers(request: PostUsersRequest) = request<PostUsersResponse>(
        method = Method.POST,
        body = request,
        specification = RestAssuredSpecification.CREATED_RESPONSE_SPEC.value
    )
}
