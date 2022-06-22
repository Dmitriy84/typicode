package com.affinidi.api.steps

import com.affinidi.api.steps.responses.PostsResponse
import com.affinidi.framework.BaseSteps
import io.qameta.allure.Step
import org.springframework.stereotype.Component


@Component
class PostsApiSteps : BaseSteps() {
    override fun getRequestSpec() = requestBase("posts")

    @Step("GET: /posts")
    fun getPosts() = request<Array<PostsResponse>>()
}
