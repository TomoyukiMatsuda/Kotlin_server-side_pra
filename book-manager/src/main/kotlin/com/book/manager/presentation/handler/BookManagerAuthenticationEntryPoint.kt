package com.book.manager.presentation.handler

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class BookManagerAuthenticationEntryPoint : AuthenticationEntryPoint {

    //未認証の状態のユーザーで認証が必要なAPIにアクセスしたときに実行される処理
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        // ステータスのみを設定 401
        response.status = HttpServletResponse.SC_UNAUTHORIZED
    }
}