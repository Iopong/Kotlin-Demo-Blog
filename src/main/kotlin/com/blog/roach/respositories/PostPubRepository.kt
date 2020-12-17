package com.blog.roach.respositories

import com.blog.roach.entities.publication.PostPub
import org.springframework.data.jpa.repository.JpaRepository

interface PostPubRepository: JpaRepository<PostPub, Long>{}