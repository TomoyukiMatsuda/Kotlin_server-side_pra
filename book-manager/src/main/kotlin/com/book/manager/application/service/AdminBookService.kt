package com.book.manager.application.service

import com.book.manager.domain.model.Book
import com.book.manager.domain.repository.BookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class AdminBookService(
    private val bookRepository: BookRepository // コンストラクタインジェクション
) {

    @Transactional
    fun register(book: Book) {
        // book を受け取ってすでに書籍データが存在すれば、例外を投げて処理をロールバック（@Transactionalのおかげ）
        bookRepository.findWithRental(book.id)?.let { throw IllegalAccessException("すでに存在する書籍ID： ${book.id}") }
        // 例外が投げられなければ、そのままbookを登録
        bookRepository.register(book)
    }

    @Transactional
    fun update(bookId: Long, title: String?, author: String?, releaseDate: LocalDate?) {
        // 該当bookIdが存在しなければ、例外を投げてロールバックする
        bookRepository.findWithRental(bookId) ?: throw IllegalAccessException("存在しない書籍ID: $bookId")
        bookRepository.update(bookId, title, author, releaseDate)
    }
}

fun main() {

}