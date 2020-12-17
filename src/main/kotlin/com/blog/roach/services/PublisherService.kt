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
    private val twitterService: TwitterService,
    private val localService: LocalService,
    private val facebookService: FacebookService
) {

    fun findPublisher(postPub: PostPub) {
        var type = postPub.pubType?.toLowerCase()

        if (type != null) {
            return when (type) {
                "twitter" -> twitterService.publishPost(postPub.post)
                "facebook" -> facebookService.publishPost(postPub.post)
                "local" -> localService.publishPost(postPub.post)
                else -> throw NotFound
            }
        }
        return throw NotFound
    }

    fun publishPost(postPub: PostPub, id: Long) {
        var type = postPub.pubType
        var post = postRepo.findById(id)

        try {
            // check if not null and post to publisher
            if (type != null && post != null) {
                var pub = pubRepo.findByPubType(type.toLowerCase())
                return (postRepo.findById(id).map {post ->
                    // tie a post to a pub
                    postPub.post = post

                    // tie a publisher to a post
                    postPub.pub = pub

                    // save it to postPub table
                    postPubRepo.save(postPub)

                    // find publisher
                    return@map findPublisher(postPub)
                }.orElseThrow { NotFound })
            } else {
                throw NotFound
            }
        } catch(e: Exception) {
            throw e
        }
    }
}