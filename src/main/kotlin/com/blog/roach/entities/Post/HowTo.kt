package com.blog.roach.entities.Post

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity(name = "howto")
@Table(name = "howto")
class HowTo(
    @Column(name = "title")
    var title: String? = null,
    @Column(name = "content")
    var content: String? = null,
) : Post()