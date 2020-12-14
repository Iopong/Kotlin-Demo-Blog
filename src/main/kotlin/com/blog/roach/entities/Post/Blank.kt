package com.blog.roach.entities.Post

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity(name = "blank")
@Table(name = "blank")
class Blank(
    @Column(name = "title")
    var title: String? = null
) : Post()