package com.blog.roach.respositories

import com.blog.roach.entities.Users
import com.blog.roach.entities.credentials.UserCredential
import org.springframework.data.jpa.repository.JpaRepository

interface CredRepository: JpaRepository<UserCredential, Long> {
    fun findByAuthor(author: Users): UserCredential
    fun findByAuthorAndPublication(author: Users, pub: String): UserCredential
}