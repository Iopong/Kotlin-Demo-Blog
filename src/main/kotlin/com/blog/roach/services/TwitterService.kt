package com.blog.roach.services

import com.blog.roach.entities.Users
import com.blog.roach.entities.post.Post
import com.blog.roach.respositories.CredRepository
import org.springframework.stereotype.Service

@Service
class TwitterService(
    private val credRepo: CredRepository
): Publisher  {

    override fun publishPost(post: Post?) {
        if (post != null) {
            var verified = post.author?.let {verifyCred(post.author)}
            if (verified == true) {
                println("twitter: ${post.title}")
            }
        }
    }

    override fun verifyCred(user: Users?): Boolean {
        if (user != null) {
            var foundUser = credRepo.findByAuthorAndPublication(user, "twitter")
            var publication = foundUser.publication

            if (publication != null) {
                return publication.toLowerCase() == "twitter"
            }
        }
        return false
    }
}