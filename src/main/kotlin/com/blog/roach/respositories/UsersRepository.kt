package com.blog.roach.respositories

import com.blog.roach.entities.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.webmvc.RepositoryRestController
import java.util.*
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Pageable

@Repository
interface UsersRepository: JpaRepository<Users, Long>{
    fun findAllById(id: Long): Users
    fun findAllByEmail(
        email: String,
    ): Users
    fun findByEmail(
        email: String,
    ): Users
}