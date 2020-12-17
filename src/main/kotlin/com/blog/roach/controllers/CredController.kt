package com.blog.roach.controllers

import com.blog.roach.entities.credentials.UserCredential
import com.blog.roach.exceptions.NotFound
import com.blog.roach.respositories.CredRepository
import com.blog.roach.respositories.UsersRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class CredController(
    val _userRepo: UsersRepository,
    val _credRepo: CredRepository
){

    private val userRepo: UsersRepository = _userRepo
    private val credRepo: CredRepository = _credRepo

    @PostMapping("/users/{id}/credentials")
    fun createCred(
        @PathVariable id: Long,
        @Valid @RequestBody user_cred: UserCredential,
        request: HttpServletRequest
    ): Optional<ResponseEntity<UserCredential>> {
        return (userRepo.findById(id).map { user ->
            user_cred.author = user
            var nCred: UserCredential = credRepo.save(user_cred)

            val createdURI = URI.create(request.requestURL.toString() + "/" + nCred.id)
            ResponseEntity.created(createdURI).body(nCred)
        }?: throw NotFound)
    }
}