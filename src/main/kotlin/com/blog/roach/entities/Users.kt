package com.blog.roach.entities

import org.springframework.hateoas.RepresentationModel
import javax.persistence.*

@Entity(name = "users")
@Table(name = "users")
class Users(
        @Column(name = "id")
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        @Column(name = "name")
        var name: String,
        @Column(name = "email")
        var email: String
) : RepresentationModel<Users>()