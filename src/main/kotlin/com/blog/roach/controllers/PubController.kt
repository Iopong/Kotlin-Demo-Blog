package com.blog.roach.controllers

import com.blog.roach.entities.publication.Publication
import com.blog.roach.exceptions.BadRequest
import com.blog.roach.exceptions.NotFound
import com.blog.roach.exceptions.StorageError
import com.blog.roach.respositories.PubRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.Exception
import java.net.URI
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

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

    @GetMapping("/publication")
    fun findAll(): ResponseEntity<MutableList<Publication>> {
        var allPubs: MutableList<Publication>

        try {
            allPubs = pubRepo.findAll()
        } catch (e: Exception) {
            throw NotFound
        }
        return ResponseEntity.ok(allPubs)
    }

    @GetMapping("/publication/{id}")
    fun findById(
        @PathVariable id: Long
    ): ResponseEntity<Optional<Publication>> {
        var onePub: Optional<Publication>

        try {
            onePub = pubRepo.findById(id)
        } catch (e: Exception) {
            throw NotFound
        }
        return ResponseEntity.ok(onePub)
    }

    @PutMapping("/publication/{id}")
    fun updatePub(
        @PathVariable id: Long,
        @Valid @RequestBody pub: Publication
    ): Publication? {
        return pubRepo.findById(id).map { nPub ->
            nPub.pubType = pub.pubType
            return@map pubRepo.save(nPub)
        }.orElseThrow { StorageError }
    }

    @DeleteMapping("/publication/{id}")
    fun deleteUser(
        @PathVariable id: Long
    ) : Optional<ResponseEntity<String>>? {
        return pubRepo.findById(id).map { dPub ->
            pubRepo.delete(dPub)
            return@map ResponseEntity.accepted().build<String>()
        }.or { throw StorageError }
    }

}