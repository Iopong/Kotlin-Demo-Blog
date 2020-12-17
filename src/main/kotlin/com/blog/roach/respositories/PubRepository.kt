package com.blog.roach.respositories

import com.blog.roach.entities.publication.Publication
import org.springframework.data.jpa.repository.JpaRepository

interface PubRepository: JpaRepository<Publication, Long> {
    fun findByPubType(pub: String): Publication
}