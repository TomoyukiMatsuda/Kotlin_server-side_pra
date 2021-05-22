package com.book.manager.application.service

import com.book.manager.domain.model.BookWithRental
import com.book.manager.domain.repository.BookRepository
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service // Repositoryと同様にDI対象とするためのもの
class BookService(private val bookRepository: BookRepository) { // コンストラクタインジェクション
    // BookRepository はインターフェースだから、
    // BookRepositoryの実装クラスのBookRepositoryImplがインジェクトされている
    fun getList(): List<BookWithRental> {
        return bookRepository.findAllWithRental()
    }

    fun getDetail(bookId: Long): BookWithRental {
        return bookRepository.findWithRental(bookId) ?: throw IllegalArgumentException("存在しない書籍ID : $bookId")
    }
}