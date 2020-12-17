package com.blog.roach.entities.post

import javax.persistence.*

@Entity(name = "blog")
@Table(name = "blog")
class Blog(
    @Column(name = "title")
    override var title: String? = null,
    @Column(name = "headline")
    var headline: String? = null,
    @Column(name = "content")
    var content: String? = null,
) : Post()
