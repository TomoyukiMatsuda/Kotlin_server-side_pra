package com.book.manager.presentation.handler

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class BookManagerAuthenticationSuccessHandler : AuthenticationSuccessHandler{

    // ログインAPIで認証成功した場合の処理を記述
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication?
    ) {
        // ステータスだけを設定　SC_OK: 200
        response.status = HttpServletResponse.SC_OK
    }
}