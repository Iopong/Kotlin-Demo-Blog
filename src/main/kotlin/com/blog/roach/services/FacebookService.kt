package com.blog.roach.services

import com.blog.roach.entities.Users
import com.blog.roach.entities.post.Post
import com.blog.roach.respositories.CredRepository
import org.springframework.stereotype.Service

@Service
class FacebookService(
    private val credRepo: CredRepository
): Publisher {

    override fun verifyCred(user: Users?): Boolean {
        if (user != null) {

            // To account for multiple cred entries
            // maybe change to findByAuthorAndPublication
            var foundUser = credRepo.findByAuthorAndPublication(user, "facebook")
            var publication = foundUser.publication

            if (publication != null) {
                return publication.toLowerCase() == "facebook"
            }
        }
        return false
    }

    override fun publishPost(post: Post?) {
        if (post != null) {
            var verified = post.author?.let {verifyCred(post.author)}
            if (verified == true) {
                println("facebook: ${post.title}")
            }
        }
    }
}