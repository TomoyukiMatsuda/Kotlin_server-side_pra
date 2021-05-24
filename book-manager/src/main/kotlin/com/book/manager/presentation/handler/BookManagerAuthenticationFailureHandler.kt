package com.book.manager.presentation.handler

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class BookManagerAuthenticationFailureHandler : AuthenticationFailureHandler {
    // ログインAPIで認証失敗したときに実行される処理
    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        exception: AuthenticationException?
    ) {
        // SC_UNAUTHORIZED：401
        response.status = HttpServletResponse.SC_UNAUTHORIZED
    }
}