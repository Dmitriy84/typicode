package com.affinidi.api.steps

import com.affinidi.api.data.responses.photos.PhotosResponse
import com.affinidi.framework.BaseSteps
import io.qameta.allure.Step
import org.springframework.stereotype.Component


@Component
class PhotosApiSteps : BaseSteps() {
    override fun getRequestSpec() = requestBase("photos")

    @Step("GET: /photos")
    fun getPhotos() = request<Array<PhotosResponse>>()
}
