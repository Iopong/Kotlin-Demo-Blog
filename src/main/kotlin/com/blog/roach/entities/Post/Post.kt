package com.blog.roach.entities.Post

import com.blog.roach.Extension.localDateFormatter
import com.blog.roach.entities.Users
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
open abstract class Post {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null
    @Basic
    @Column(name = "createdAt", nullable = false, updatable = false)
    open var createdAt: LocalDateTime = LocalDateTime.now()
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    var author: Users? = null
}