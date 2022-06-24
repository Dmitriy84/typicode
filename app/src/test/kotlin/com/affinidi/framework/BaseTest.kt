package com.affinidi.framework

import com.affinidi.api.steps.AlbumsApiSteps
import com.affinidi.api.steps.CommentsApiSteps
import com.affinidi.api.steps.PhotosApiSteps
import com.affinidi.api.steps.PostsApiSteps
import com.affinidi.api.steps.UsersApiSteps
import com.affinidi.framework.config.ApplicationProps
import com.affinidi.framework.config.TestConfig
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension


@ExtendWith(SpringExtension::class)
@EnableConfigurationProperties(ApplicationProps::class)
@TestPropertySource("classpath:profiles/application-\${spring.profiles.active}.properties")
@SpringBootTest(classes = [TestConfig::class])
abstract class BaseTest {
    @Autowired
    protected lateinit var postsApiSteps: PostsApiSteps

    @Autowired
    protected lateinit var albumsApiSteps: AlbumsApiSteps

    @Autowired
    protected lateinit var photosApiSteps: PhotosApiSteps

    @Autowired
    protected lateinit var usersApiSteps: UsersApiSteps

    @Autowired
    protected lateinit var commentsApiSteps: CommentsApiSteps
}
