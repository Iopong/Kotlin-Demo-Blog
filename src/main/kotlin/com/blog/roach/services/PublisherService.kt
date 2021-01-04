package com.blog.roach.services

import com.blog.roach.entities.publication.PostPub
import com.blog.roach.exceptions.NotFound
import com.blog.roach.respositories.PostPubRepository
import com.blog.roach.respositories.PostsRepository
import com.blog.roach.respositories.PubRepository
import org.springframework.stereotype.Service


@Service
class PublisherService(
    private val postPubRepo: PostPubRepository,
    private val pubRepo: PubRepository,
    private val postRepo: PostsRepository,
    private val services: List<Publisher>
) {
    fun publishPost(postPub: PostPub, id: Long): PostPub? {

        try {
            // Get the type from the postPub object
            var type = postPub.pubType

            // check if post id matches any post in db.
            return postRepo.findById(id).map { post ->
                if (type != null) {
                    var pub = pubRepo.findByPubType(type.toLowerCase())
                    postPub.post = post
                    postPub.pub = pub

                    // find services that matches our type.
                    services.filter { sv ->
                        sv.type == type.toLowerCase()
                    }.forEach { s ->
                        if (post != null) {
                            s.publishPost(post)
                        }
                    }
                }

                // we're done so save postPub to repo.
                postPubRepo.save(postPub)
            }.orElseThrow { NotFound }
        } catch(e: Exception) {
            throw e
        }
    }
}