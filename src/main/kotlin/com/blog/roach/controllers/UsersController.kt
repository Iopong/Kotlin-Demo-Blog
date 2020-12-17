package com.blog.roach.controllers

import com.blog.roach.exceptions.BadRequest
import com.blog.roach.exceptions.NotFound
import com.blog.roach.exceptions.StorageError
import com.blog.roach.entities.Users
import com.blog.roach.respositories.UsersRepository
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


@RestController
@RequestMapping("/api")
class UsersController(
    val _userRepo: UsersRepository,
)
{
    private val usersRepository: UsersRepository = _userRepo

    @GetMapping("/users")
    fun findAll(
        request: HttpServletRequest
    ): ResponseEntity<MutableIterable<Users>> {
        var allUsers: MutableIterable<Users>

        try {
            allUsers = usersRepository.findAll()
        } catch (e: Exception) {
            throw NotFound
        }

        return ResponseEntity.ok(allUsers)
    }

    @GetMapping("/users/{id}")
    fun findById(
        @PathVariable id : Long
    ): ResponseEntity<Optional<Users>> {
        var oneUser: Optional<Users>
        try {
            oneUser = usersRepository.findById(id)
        } catch (e: Exception) {
            throw NotFound
        }
        return ResponseEntity.ok(oneUser)
    }

    @RequestMapping(
        value = ["/users"],
        params = ["email"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun findByEmail(
        request: HttpServletRequest,
        @RequestParam("email") email: String
    ): ResponseEntity<Users> {
        val oneUser: Users

        try {
            oneUser = usersRepository.findByEmail(email)
        } catch (e: Exception) {
            throw NotFound
        }
        return ResponseEntity.ok(oneUser)
    }

    @PutMapping("/users/{id}")
    fun updateUsers(
        @PathVariable id: Long,
        @Valid @RequestBody user: Users
    ): Users {
        return usersRepository.findById(id).map { upUser ->
            upUser.email = user.email
            upUser.name = user.name
            return@map usersRepository.save(upUser)
        }.orElseThrow { StorageError }
    }

    @DeleteMapping("/users/{id}")
    fun deleteUser(
        @PathVariable id: Long
    ): ResponseEntity<String>? {
        return usersRepository.findById(id).map{ dUser ->
            usersRepository.delete(dUser)
            return@map ResponseEntity.ok().build<String>()
        }.orElseThrow{ StorageError }
    }

    @PostMapping("/users")
    fun createUsers(
        @RequestBody user: Users,
        request: HttpServletRequest
    ): ResponseEntity<Users> {
        var createdUser: Users

        try {
            createdUser = usersRepository.save(user)
        } catch (e: Exception) {
            throw BadRequest
        }

        val createdURI = URI.create(request.requestURL.toString() + "/" + createdUser.id)
        return ResponseEntity.created(createdURI).body(createdUser);
    }
}