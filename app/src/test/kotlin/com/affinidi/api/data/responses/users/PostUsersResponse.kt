package com.affinidi.api.data.responses.users

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)
data class PostUsersResponse(
    val accessToken: String?,
    val user: User?
) {
    data class User(
        val email: String,
        val id: Int
    )
}
