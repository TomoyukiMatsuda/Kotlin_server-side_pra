package com.book.manager.presentation.controller

import com.book.manager.application.service.AdminBookService
import com.book.manager.domain.model.Book
import com.book.manager.presentation.form.RegisterBookRequest
import com.book.manager.presentation.form.UpdateBookRequest
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("admin/book")
class AdminBookController(
    private val adminBookService: AdminBookService // ServiceをDI
) {
    // 書籍登録
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterBookRequest) {
        // DIしたServiceから register を呼ぶ
        adminBookService.register(
            Book(
                request.id,
                request.title,
                request.author,
                request.releaseDate
            )
        )
    }

    // 書籍データ更新
    @PutMapping("/update")
    fun update(@RequestBody request: UpdateBookRequest) {
        // Service の処理を呼ぶ
        adminBookService.update(request.id, request.title, request.author, request.releaseDate)
    }
}