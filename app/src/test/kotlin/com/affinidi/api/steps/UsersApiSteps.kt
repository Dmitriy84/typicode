package com.affinidi.api.steps

import com.affinidi.api.steps.responses.UsersResponse
import com.affinidi.framework.BaseSteps
import io.qameta.allure.Step
import org.springframework.stereotype.Component


@Component
class UsersApiSteps : BaseSteps() {
    override fun getRequestSpec() = requestBase("users")

    @Step("GET: /users")
    fun getUsers() = request<Array<UsersResponse>>()
}
