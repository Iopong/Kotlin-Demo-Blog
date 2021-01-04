package com.blog.roach


import com.blog.roach.entities.publication.Publication
import com.blog.roach.respositories.PubRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.assertj.core.api.Assertions.assertThat

@DataJpaTest
class PubTests {

    @Autowired
    lateinit var pubRepository: PubRepository

    // member variables
    var twitter: Publication? = null
    var facebook: Publication? = null

    // constants
    val twitterName = "twitter"
    val facebookName = "facebook"

    @BeforeEach
    fun populate() {
        var twitterPub = Publication()
        twitterPub.pubType = "twitter"
        twitter = pubRepository.save(twitterPub)

        var facebookPub = Publication()
        facebookPub.pubType = "facebook"
        facebook = pubRepository.save(facebookPub)
    }

    @Test
    fun `Test POST create a publication`() {
        twitter?.let { pub ->
            var nPub = pubRepository.findByPubType(twitterName)
            assertThat(nPub).isEqualTo(pub)
        }

        facebook?.let { pub ->
            var nPub = pubRepository.findByPubType(twitterName)
            assertThat(nPub).isEqualTo(pub)
        }
    }
}