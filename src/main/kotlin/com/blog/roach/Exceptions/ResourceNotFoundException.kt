package com.blog.roach.Exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
object StorageError : java.lang.RuntimeException("Internal storage error")

@ResponseStatus(HttpStatus.NOT_FOUND)
object NotFound : RuntimeException("Not found")

@ResponseStatus(HttpStatus.BAD_REQUEST)
object BadRequest : RuntimeException("Bad Request")