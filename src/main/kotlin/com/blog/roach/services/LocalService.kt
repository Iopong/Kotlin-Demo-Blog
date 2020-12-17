package com.blog.roach.services

import com.blog.roach.entities.Users
import com.blog.roach.entities.post.Post
import com.blog.roach.respositories.PostsRepository
import org.springframework.stereotype.Service

@Service
class LocalService(
    private val postRepo: PostsRepository
    ): Publisher
{
    override fun publishPost(post: Post?) {
        if (post != null) {
            post.id?.let { postRepo.findById(it).map { post ->
                println("local: ${post.title}")
            }}
        }
    }

    override fun verifyCred(user: Users?): Boolean {
        TODO("Not yet implemented")
    }
}