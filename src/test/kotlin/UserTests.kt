package com.blog.roach


import com.blog.roach.entities.Users
import com.blog.roach.respositories.BlogRepository
import com.blog.roach.respositories.UsersRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class UserTests {


    @Autowired
    lateinit var userRepository: UsersRepository

    var testUser: Users? = null

    @BeforeEach
    fun populate() {
        val user1 = Users(
            null,
            "bruce wayne",
            "bwayne@email.com"
        )
        val createdUser: Users = userRepository.save(user1)
        testUser = createdUser
    }

    @Test
    fun `Test POST create a user`() {
        val user1 = Users(
            null,
            "brad pitt",
            "bpitt@email.com"
        )
        val createdUser: Users = userRepository.save(user1)
        createdUser.id?.let {
            userRepository.findById(it).map{ foundUser ->
                assertThat(foundUser).isEqualTo(createdUser)
            }
        }
    }

    @Test
    fun `Test GET by email`() {
        val user = userRepository.findAllByEmail("bwayne@email.com")
        assertThat(user).isEqualTo(testUser)
    }

    @Test
    fun `Test GET by id`() {
        testUser?.id?.let { userRepository.findById(it) }?.map { user ->
            assertThat(user).isEqualTo(testUser)
        }
    }

    @Test
    fun `Test PUT by id`() {
        val email = "putTest@email.com"
        val name = "put test"

        testUser?.id?.let { userRepository.findById(it) }?.map { user ->
            user.email = email
            user.name = name

            var newUser = userRepository.save(user)

            assertThat(newUser.email).isEqualTo(email)
            assertThat(newUser.name).isEqualTo(name)
        }
    }

    @Test
    fun `Test DELETE by id`() {
        // get the current id
        val id = testUser?.id

        // delete the user
        testUser?.let { userRepository.delete(it) }

        // check that user is null
        if (id != null) {
            userRepository.findById(id).map{ user ->
                assertThat(user).isNull()
            }
        }
    }
}