package com.book.manager.application.service

import com.book.manager.domain.model.BookWithRental
import com.book.manager.domain.repository.BookRepository
import org.springframework.stereotype.Service

@Service // Repositoryと同様にDI対象とするためのもの
class BookService(private val bookRepository: BookRepository) { // コンストラクタインジェクション
    // BookRepository はインターフェースだから、
    // BookRepositoryの実装クラスのBookRepositoryImplがインジェクトされている
    fun getList(): List<BookWithRental> {
        return bookRepository.findAllWithRental()
    }
}