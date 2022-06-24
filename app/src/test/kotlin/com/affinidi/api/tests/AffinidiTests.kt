package com.affinidi.api.tests

import com.affinidi.api.data.request.posts.PostPostsRequest
import com.affinidi.api.data.request.users.PostUsersRequest
import com.affinidi.api.data.responses.RestAssuredSpecification
import com.affinidi.api.data.responses.users.GetUsersResponse
import com.affinidi.framework.BaseTest
import com.affinidi.framework.config.BaseRestAssuredConfig
import com.affinidi.framework.data.RandomString
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import io.restassured.module.jsv.JsonSchemaValidator
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import net.javacrumbs.jsonunit.assertj.assertThatJson
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
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
                        GetUsersResponse.Geo(-71.4197, 71.7478),
                        users[4].address.geo
                    ) { "5th user coordinates is as not expected" }
                }
            )
        }
    }

    @Test
    @Story("Comments")
    fun `get comments with postId sorted in descending order and verify HTTP response status code and then nerify that records are sorted in response`() {
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

    @Test
    @Story("Users")
    fun `register new user and verify HTTP response status code and then verify that access token is present is response body`() {
        val newUser = PostUsersRequest(
            "${RandomString.getRandomString(10)}@gmail.com",
            RandomString.getRandomString(10)
        )
        val (user, response) = usersApiSteps.postUsers(newUser)

        When {
            response
        } Then {
            assertAll(
                {
                    assertThat(
                        "new user in response should have valid token",
                        user.accessToken?.length ?: 0,
                        greaterThan(100)
                    )
                }
            )
        }
    }

    @Test
    @Story("Posts")
    fun `create post with adding access token in header and verify HTTP response status code and then verify post is created`() {
        val newUser = usersApiSteps.postUsers(
            PostUsersRequest(
                "${RandomString.getRandomString(10)}@gmail.com",
                RandomString.getRandomString(10)
            )
        ).first

        val newPost = PostPostsRequest(
            " title ${RandomString.getRandomString(10)}",
            "body ${RandomString.getRandomString(100)}"
        )
        Given {
            BaseRestAssuredConfig.authSpec(newUser.accessToken ?: "", postsApiSteps.applicationProps.baseURI)
                .basePath("posts").body(newPost)
        } When {
            post()
        } Then {
            spec(RestAssuredSpecification.CREATED_RESPONSE_SPEC.value)
            assertAll(
                {
                    assertThat().body(
                        JsonSchemaValidator.matchesJsonSchema(javaClass.getResource("/data/posts_response_schema.json"))
                    )
                },
                {
                    assertThatJson(extract().asString().trim()).isEqualTo(
                        """
                    {
                        "title": "${newPost.title}",
                        "body": "${newPost.body}",
                        "id": "#{json-unit.ignore}",
                        "userId": ${newUser.user?.id}
                    }
                """.trimIndent()
                    )
                }
            )
        }
    }
}
