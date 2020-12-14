package com.blog.roach


import com.blog.roach.entities.Post.Blog
import com.blog.roach.entities.Users
import com.blog.roach.respositories.BlogRepository
import com.blog.roach.respositories.UsersRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDateTime

@DataJpaTest
class PostTests {

    @Autowired
    lateinit var userRepository: UsersRepository

    @Autowired
    lateinit var postRepository: BlogRepository

    // member variables
    var time: LocalDateTime = LocalDateTime.parse("2020-12-10T15:56:57.806101")
    var testUser: Users? = null
    var testBlog: Blog? = null

    @BeforeEach
    fun populate() {

        // create a user/author
        var user1 = Users(
            null,
            "bruce wayne",
            "bwayne@email.com"
        )

        // create a post of blog type
        var post1 = Blog(
            "first blog",
            "headline for first blog",
            "content for first blog",
        )

        // add author and createAt to post
        post1.author = user1
        post1.createdAt = time

        // save user to repo
        testUser = userRepository.save(user1)

        // save blog to repo
        testBlog = postRepository.save(post1)
    }

    @Test
    fun `Test POST create a post`() {
        testUser?.let { user ->
            var post = postRepository.findByAuthor(user)
            assertThat(post).isEqualTo(testBlog)
        }
    }

    @Test
    fun `Test GET fetch by author`() {
        testUser?.let { user ->
            var post = postRepository.findByAuthor(user)
            assertThat(post).isEqualTo(testBlog)
        }
    }

    @Test
    fun `Test GET by author and time`() {
        testUser?.let { user ->
            var nPost = postRepository.findByCreatedAtAndAuthor(time, user)
            assertThat(nPost).isEqualTo(testBlog)
        }
    }

    @Test
    fun `Test PUT update by an author id`() {
        testBlog?.let { blog ->
            blog.content = "modified content"
            var nBlog = postRepository.save(blog)
            assertThat(nBlog).isEqualTo(blog)
        }
    }

    @Test
    fun `Test DELETE delete by an author id`() {
        val id = testBlog?.id
        testBlog?.let { blog ->
            postRepository.delete(blog)
        }

        if (id !== null) {
            postRepository.findById(id).map{ blog ->
                assertThat(blog).isNull()
            }
        }
    }
}

