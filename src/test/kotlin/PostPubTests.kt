package com.blog.roach

import com.blog.roach.entities.post.Blog
import com.blog.roach.entities.publication.PostPub
import com.blog.roach.entities.publication.Publication
import com.blog.roach.respositories.PostPubRepository
import com.blog.roach.respositories.PostsRepository
import com.blog.roach.respositories.PubRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.assertj.core.api.Assertions.assertThat

@DataJpaTest
class PostPubTests
{
    @Autowired
    lateinit var postPubRepository: PostPubRepository

    @Autowired
    lateinit var pubRepository: PubRepository

    @Autowired
    lateinit var postsRepository: PostsRepository

    // member variable
    var testPostPub: PostPub? = null

    @BeforeEach
    fun populate() {

        // create and save post
        var post1 = Blog(
            "test first blog",
            "test headline",
            "great first test post"
        )
        postsRepository.save(post1)

        // create and save publication
        var pub1 = Publication()
        pub1.pubType = "twitter"
        pubRepository.save(pub1)

        // create and save postPub
        var postPub = PostPub()
        postPub.post = post1
        postPub.pub = pub1
        testPostPub = postPubRepository.save(postPub)

    }

    @Test
    fun `Test POST create a post pub`() {
        testPostPub?.let { postPb ->
            postPb.id?.let { id ->
                var newPostPub = postPubRepository.findById(id)
                assertThat(newPostPub).isEqualTo(testPostPub)
            }
        }
    }
}