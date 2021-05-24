package com.book.manager.presentation.handler

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class BookManagerAccessDeniedHandler : AccessDeniedHandler {

    // 必要なアクセス権限を持っていないユーザーでAPIにアクセスしたときに実行される処理
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException?
    ) {
        // 403
        response.status = HttpServletResponse.SC_FORBIDDEN
    }
}