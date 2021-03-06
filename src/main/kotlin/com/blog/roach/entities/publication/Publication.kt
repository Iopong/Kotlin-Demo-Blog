package com.blog.roach.entities.publication

import org.springframework.hateoas.RepresentationModel
import javax.persistence.*

@Entity(name = "publication")
@Table(name = "publication")
class Publication: RepresentationModel<Publication>() {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @Column(name = "pubType")
    var pubType: String? = null
}