package com.blog.roach.controllers

import com.blog.roach.exceptions.NotFound
import com.blog.roach.entities.post.Blog
import com.blog.roach.entities.post.Post
import com.blog.roach.entities.Users
import com.blog.roach.respositories.PostsRepository
import com.blog.roach.respositories.UsersRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.time.LocalDateTime
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


@RestController
@RequestMapping("/api")
class PostsController(
    val _postsRepo: PostsRepository,
    val _userRepo: UsersRepository,
)
{
    private val postsRepository: PostsRepository = _postsRepo
    private val usersRepository: UsersRepository = _userRepo

    @PostMapping("/users/{id}/posts")
    fun createPost(
        @PathVariable id: Long,
        @Valid @RequestBody posts: Blog,
        request: HttpServletRequest,
    ): Optional<ResponseEntity<Blog>>? {
        return usersRepository.findById(id).map{ user ->
            posts.author = user
            var nPost: Blog = postsRepository.save(posts)

            val createdURI = URI.create(request.requestURL.toString() + "/" + nPost.id)
            ResponseEntity.created(createdURI).body(nPost)
        }?: throw NotFound
    }

    @RequestMapping(
        value = ["/users/{id}/posts"],
        params = ["date"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getByDate(
        @PathVariable id: Long,
        request: HttpServletRequest,
        @RequestParam("date") date: String,
    ) : ResponseEntity<Post> {
        return usersRepository.findById(id).map { user ->
            val nDate: LocalDateTime = LocalDateTime.parse(date)
            var nPost = postsRepository.findByCreatedAtAndAuthor(
                nDate, user)
            return@map ResponseEntity.ok(nPost)
        }.orElseThrow { NotFound }
    }


    @GetMapping("/users/{id}/posts")
    fun getAllPostsByUserId(
        @PathVariable id: Long,
        pageable: Pageable
    ): ResponseEntity<MutableList<Post>>? {
        return usersRepository.findById(id).map { user ->
            return@map ResponseEntity.ok(
                postsRepository.findAllByAuthor(user, pageable)
            )
        }.orElseThrow { NotFound }
    }


    @PutMapping("/users/{userId}/posts/{postId}")
    fun updatePost(
        @PathVariable userId: Long,
        @PathVariable postId: Long,
        @Valid @RequestBody upPost: Blog,
    ): Post {
        var exPost: Blog? = postsRepository.findByIdOrNull(postId) as Blog
        var user: Users? = usersRepository.findByIdOrNull(userId)

        if (exPost == null || user == null) throw NotFound

        exPost.title = upPost.title
        exPost.content = upPost.content
        exPost.headline = upPost.headline
        exPost.author = user

        return postsRepository.save(exPost)
    }

    @DeleteMapping("/users/{userId}/posts/{postId}")
    fun deletePost(
        @PathVariable (value = "userId") userId: Long,
        @PathVariable (value = "postId") postId: Long,
    ): ResponseEntity<String> {
        var exPost: Post? = postsRepository.findByIdOrNull(postId)
        var user: Users? = usersRepository.findByIdOrNull(userId)

        if (exPost == null || user == null) throw NotFound

        postsRepository.delete(exPost)
        return ResponseEntity.ok().build<String>()
    }
}