package com.affinidi.api.steps.responses


data class CommentsResponse(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
)
