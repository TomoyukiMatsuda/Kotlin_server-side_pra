package com.book.manager.presentation.form

import com.book.manager.domain.model.BookWithRental
import com.book.manager.domain.model.Rental
import java.time.LocalDate
import java.time.LocalDateTime

//機能によってはほぼ同様の実装になる場合もあるが、
// 必ずドメインオブジェクトとは別のクラスとして定義するようにしている
data class GetBookListResponse(val bookList: List<BookInfo>)

data class BookInfo(
    val id: Long,
    val title: String,
    val author: String,
    val isRental: Boolean
) {
    // 今回のthis はプライマリコンストラクタを指しており、呼んでいる。セカンダリコンストラクタ は最終的に必ずプライマリコンストラクタを呼び出す必要がある
    constructor(model: BookWithRental) : this(model.book.id, model.book.title, model.book.author, model.isRental)
}

data class GetBookDetailResponse(
    val id: Long,
    val title: String,
    val author: String,
    val releaseDate: LocalDate,
    val rentalInfo: RentalInfo?
) {
    constructor(model: BookWithRental) : this(
        model.book.id,
        model.book.title,
        model.book.author,
        model.book.releaseDate,
        model.rental?.let { RentalInfo(model.rental) }
    )
}

data class RentalInfo(
    val userId: Long,
    val rentalDatetime: LocalDateTime,
    val returnDeadline: LocalDateTime
) {
    constructor(rental: Rental) : this(rental.userId, rental.rentalDatetime, rental.returnDeadline)
}

// 書籍登録のリクエスト
data class RegisterBookRequest(
    val id: Long,
    val title: String,
    val author: String,
    val releaseDate: LocalDate
)

// 書籍データ更新処理のリクエスト
data class UpdateBookRequest(
    val id: Long,
    val title: String?,
    val author: String?,
    val releaseDate: LocalDate?
)