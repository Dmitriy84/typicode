package com.affinidi.api.steps

import com.affinidi.api.data.request.posts.PostPostsRequest
import com.affinidi.api.data.responses.posts.PostsResponse
import com.affinidi.framework.BaseSteps
import io.qameta.allure.Step
import io.restassured.http.Method
import org.springframework.stereotype.Component


@Component
class PostsApiSteps : BaseSteps() {
    override fun getRequestSpec() = requestBase("posts")

    @Step("GET: /posts")
    fun getPosts() = request<Array<PostsResponse>>()
}
