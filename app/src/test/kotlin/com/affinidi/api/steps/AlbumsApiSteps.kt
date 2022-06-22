package com.affinidi.api.steps

import com.affinidi.api.steps.responses.AlbumsResponse
import com.affinidi.framework.BaseSteps
import io.qameta.allure.Step
import org.springframework.stereotype.Component


@Component
class AlbumsApiSteps : BaseSteps() {
    override fun getRequestSpec() = requestBase("albums")

    @Step("GET: /albums/{0}")
    fun getAlbum(albumId: Int) = request<AlbumsResponse>(albumId.toString(),)
}
