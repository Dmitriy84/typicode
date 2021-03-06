package com.affinidi.api.steps

import com.affinidi.api.data.responses.comments.CommentsResponse
import com.affinidi.framework.BaseSteps
import io.qameta.allure.Step
import org.springframework.stereotype.Component


@Component
class CommentsApiSteps : BaseSteps() {
    override fun getRequestSpec() = requestBase("comments")

    @Step("GET: /comments?postId={0}")
    fun getCommentsWith(queryParams: Map<String, Any>) = request<Array<CommentsResponse>>(parametersMap = queryParams)
}
