package com.affinidi

import com.affinidi.api.steps.responses.UsersResponse
import com.affinidi.framework.BaseTest
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.lessThan
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.time.Duration.Companion.minutes


@Epic("Affinidi test task")
@Feature("Api testing of endpoints")
class AffinidiTests : BaseTest() {
    @Test
    @Story("Posts")
    fun `get all posts and verify HTTP response status code and content type`() {
        postsApiSteps.getPosts()
    }

    @Test
    @Story("Albums")
    fun `get third album (path parameter) and verify content length`() {
        val (_, response) = albumsApiSteps.getAlbum(3)

        When {
            response
        } Then {
            assertAll(
                { assertEquals(61, response.body().asString().length) { "body length should have expected length" } }
            )
        }
    }

    @Test
    @Story("Photos")
    fun `verify response time for photos endpoint is less than 10 seconds`() {
        Given {
            photosApiSteps.getRequestSpec()
        } When {
            photosApiSteps.getPhotos().second
        } Then {
            time(lessThan(10.minutes.inWholeMilliseconds))
        }
    }

    @Test
    @Story("Users")
    fun `get all users and verify HTTP response status code and then verify the 5th user geo coordinates`() {
        val (users, response) = usersApiSteps.getUsers()

        When {
            response
        } Then {
            assertAll(
                {
                    assertEquals(
                        UsersResponse.Geo(-31.8129, 62.5342),
                        users[4].address.geo
                    ) { "5th user coordinates is as not expected" }
                }
            )
        }
    }

    @Test
    @Story("Comments")
    fun `Get comments with postId sorted in descending order and verify HTTP response status code and then nerify that records are sorted in response`() {
        val (posts, _) = postsApiSteps.getPosts()
        val randomPostId = posts.map { it.id }.random()

        val (comments, response) = commentsApiSteps.getCommentsWith(
            mapOf(
                "postId" to randomPostId,
                "_sort" to "id",
                "_order" to "desc"
            )
        )

        When {
            response
        } Then {
            val actualCommentsOrderedById = comments.map { it.id }
            assertAll(
                {
                    assertEquals(
                        actualCommentsOrderedById,
                        actualCommentsOrderedById.sortedDescending()
                    ) { "comments are not correctly sorted" }
                }
            )
        }
    }
}
