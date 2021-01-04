package com.blog.roach

import com.blog.roach.entities.Users
import com.blog.roach.entities.credentials.UserCredential
import com.blog.roach.respositories.CredRepository
import com.blog.roach.respositories.UsersRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.assertj.core.api.Assertions.assertThat


@DataJpaTest
class CredTests {

    @Autowired
    lateinit var credRepository: CredRepository

    @Autowired
    lateinit var usersRepository: UsersRepository

    // member variables
    var testUser: Users? = null
    var testCred: UserCredential? = null
    val publication: String = "twitter"

    @BeforeEach
    fun populate() {

        // create a user/author
        var user1 = Users(
            null,
            "bruce wayne",
            "bwayne@email.com"
        )

        testUser  = usersRepository.save(user1)

        // create user credential
        var userCred = UserCredential(
            "bwayne@email.com",
            "goaway",
        )

        userCred.publication = publication
        userCred.author = user1

        testCred = credRepository.save(userCred)
    }

    @Test
    fun `Test POST create a cred`() {
        testUser?.let { user ->
            var cred = credRepository.findByAuthorAndPublication(user, publication)
            assertThat(cred).isEqualTo(testCred)
        }
    }
}