package com.blog.roach

import com.blog.roach.entities.post.Blog
import com.blog.roach.entities.Users
import com.blog.roach.respositories.PostsRepository
import com.blog.roach.respositories.UsersRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RoachConfig {

    @Bean
    fun databaseInit(
        usersRepository: UsersRepository,
        postsRepository: PostsRepository
    ) = ApplicationRunner {

        // create a user
        var user1: Users = Users(
            null,
            "mic jager",
            "mjag@email.com"
        );

        var user2: Users = Users(
            null,
            "barry Allen",
            "ballen@email.com"
        );

        // create a post1
        var posts1: Blog = Blog(
            "BatMan!",
            "Episode 1",
            "From the beginning..."
        )

        // create a post2
        var posts2: Blog = Blog(
            "The Flash",
            "Episode 1",
            "From the beginning..."
        )

        // tie user to a post
        posts1.author = user1
        posts2.author = user2

        // save them to db
        val savedBatMan = usersRepository.save(user1)
        println("\n\nSaved savedBatMan\n\n")
        println(savedBatMan.id)
        println(savedBatMan.name)
        println(savedBatMan.email)

        val savedBatManPost = postsRepository.save(posts1)
        println("\n\nSaved Postssss\n\n")
        println(savedBatManPost.id)
        println(savedBatManPost.title)
        println(savedBatManPost.headline)
        println(savedBatManPost.content)

        // save them to db
        val savedFlash = usersRepository.save(user2)
        println("\n\nSaved savedFlash\n\n")
        println(savedFlash.id)
        println(savedFlash.name)
        println(savedFlash.email)

        val savedFlashPost = postsRepository.save(posts2)
        println("\n\nSaved savedFlashPost\n\n")
        println(savedFlashPost.id)
        println(savedFlashPost.title)
        println(savedFlashPost.headline)
        println(savedFlashPost.content)


    }
}