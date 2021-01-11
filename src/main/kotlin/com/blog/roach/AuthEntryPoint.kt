package com.blog.roach

import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import java.io.PrintWriter
import javax.naming.AuthenticationException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthEntryPoint: BasicAuthenticationEntryPoint() {

    @Override
    @Throws(IOException::class, ServletException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: org.springframework.security.core.AuthenticationException?
    ) {

        // using getter property of kotlin.
        // realm name or getRealmName is a function
        // of BasicAuthenticationEntryPoint
        val basicRealm = "Basic Realm = $realmName"
        response.addHeader(
            "www-Authenticate", basicRealm
        )
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val writer: PrintWriter = response.writer
        if (authException != null) {
            writer.println("HTTP STATUS 401 - " + authException.message)
        }
    }

    @Override
    @Throws(Exception::class)
    override fun afterPropertiesSet() {
        realmName = "testRealm"
        super.afterPropertiesSet()
    }
}