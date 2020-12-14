package com.blog.roach.respositories

import com.blog.roach.entities.Post.Blog
//import com.blog.roach.entities.Post.Posts
import com.blog.roach.entities.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface BlogRepository: JpaRepository<Blog, Long> {
    fun findByAuthor(author: Users): Blog
    fun findByCreatedAt(createdAt: LocalDateTime): Blog
    fun findByCreatedAtAndAuthor(createdAt: LocalDateTime, author: Users): Blog
}