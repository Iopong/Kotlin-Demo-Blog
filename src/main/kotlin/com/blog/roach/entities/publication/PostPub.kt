package com.blog.roach.entities.publication

import com.blog.roach.entities.post.Post
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity(name = "post_pub")
@Table(name = "post_pub")
class PostPub {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post", nullable = false)
    @JsonIgnore
    var post: Post? = null
    @Column(name = "pub_type")
    var pubType: String? = null
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "publisher", nullable = false)
    @JsonIgnore
    var pub: Publication? = null
}
