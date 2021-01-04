package com.blog.roach.services

import com.blog.roach.entities.Users
import com.blog.roach.entities.post.Post

interface Publisher {
    val type: String
        get() = "local"
    fun publishPost(postPub: Post?)
    fun verifyCred(user: Users?): Boolean
}