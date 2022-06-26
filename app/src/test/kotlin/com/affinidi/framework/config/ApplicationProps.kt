package com.affinidi.framework.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding


@ConstructorBinding
@ConfigurationProperties(prefix = "application")
class ApplicationProps {
    lateinit var baseURI: String
}
