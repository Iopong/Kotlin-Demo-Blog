package com.blog.roach.controllers

import com.blog.roach.entities.publication.PostPub
import com.blog.roach.entities.publication.Publication
import com.blog.roach.exceptions.NotFound
import com.blog.roach.respositories.PostPubRepository
import com.blog.roach.respositories.PostsRepository
import com.blog.roach.respositories.PubRepository
import com.blog.roach.services.PublisherService
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class PostPubController(
    val _pubService: PublisherService
) {


    private val pubService: PublisherService = _pubService

    @PostMapping("/posts/{id}/postpub")
    fun createPostPub(
        @PathVariable id: Long,
        @Valid @RequestBody postPub: PostPub
    ) {
        // get the pusher type (e.g. twitter, facebook..)
        return pubService.publishPost(postPub, id)
    }
}