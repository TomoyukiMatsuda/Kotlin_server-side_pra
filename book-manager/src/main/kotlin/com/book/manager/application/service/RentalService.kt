package com.book.manager.application.service

import com.book.manager.domain.model.Rental
import com.book.manager.domain.repository.BookRepository
import com.book.manager.domain.repository.RentalRepository
import com.book.manager.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

// 貸出期間
private const val RENTAL_TERM_DAYS = 14L

@Service
class RentalService(
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
    private val rentalRepository: RentalRepository
) {

    @Transactional
    fun startRental(bookId: Long, userId: Long) {
        // userIdからユーザーを検索する
        val user = userRepository.find(userId) ?: throw IllegalAccessException("存在しないユーザーID: $userId" )
        // bookIdから書籍情報を検索する
        val book = bookRepository.findWithRental(bookId) ?: throw IllegalAccessException("存在しない書籍ID: $bookId" )
        // 貸出中のチェック
        if (book.isRental) throw IllegalAccessException("${book.book.title}は貸出中です")
        // Rentalインスタンスを生成
        val rentalDatetime = LocalDateTime.now()
        val returnDeadline = rentalDatetime.plusDays(RENTAL_TERM_DAYS)
        val rental = Rental(bookId, userId, rentalDatetime, returnDeadline)
        // レンタル開始処理
        rentalRepository.startRental(rental)
    }
}