package com.blog.roach.entities.post

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity(name = "blank")
@Table(name = "blank")
class Blank(
    @Column(name = "title")
    override var title: String? = null
) : Post()