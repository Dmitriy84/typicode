package com.affinidi.api.data.responses.comments


data class CommentsResponse(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
)
