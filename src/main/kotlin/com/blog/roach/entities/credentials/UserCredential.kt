package com.blog.roach.entities.credentials

import com.blog.roach.entities.Users
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.hateoas.RepresentationModel
import javax.persistence.*

@Entity(name = "user_credential")
@Table(name = "user_credential")
class UserCredential(
    @Column(name = "username")
    val username: String? = null,
    @Column(name = "password")
    val password: String? = null,
) : RepresentationModel<UserCredential>() {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    @Column(name = "pub_type")
    var publication: String? = null
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author", nullable = false)
    @JsonIgnore
    var author: Users? = null
}