package com.blog.roach.respositories

import com.blog.roach.entities.post.Post
import com.blog.roach.entities.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface PostsRepository: JpaRepository<Post, Long> {
    fun findByAuthor(author: Users): Post
    fun findAllByAuthor(author: Users): List<Post>
    fun findByCreatedAt(createdAt: LocalDateTime): Post
    fun findByCreatedAtAndAuthor(createdAt: LocalDateTime, author: Users): Post
}