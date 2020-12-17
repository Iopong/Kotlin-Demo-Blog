package com.blog.roach.controllers

import com.blog.roach.entities.publication.Publication
import com.blog.roach.exceptions.BadRequest
import com.blog.roach.respositories.PubRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception
import java.net.URI
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api")
class PubController(
    val _pubRepo: PubRepository
) {
    private val pubRepo: PubRepository = _pubRepo

    @PostMapping("/publication")
    fun createPub(
        @RequestBody pub: Publication,
        request: HttpServletRequest
    ): ResponseEntity<Publication> {
        var createdPub: Publication
        try {
            createdPub = pubRepo.save(pub)
        } catch (e: Exception) {
            throw BadRequest
        }
        val createdURI = URI.create(request.requestURL.toString() + "/" + createdPub.id)
        return ResponseEntity.created(createdURI).body(createdPub);
    }
}