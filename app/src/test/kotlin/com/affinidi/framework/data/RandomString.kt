package com.affinidi.framework.data

object RandomString {
    fun getRandomString(length: Int) = List(length) { (('a'..'z') + ('A'..'Z') + ('0'..'9')).random() }
        .joinToString("")
}
