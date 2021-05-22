package com.book.manager.presentation.form

import com.book.manager.domain.model.BookWithRental

//機能によってはほぼ同様の実装になる場合もあるが、
// 必ずドメインオブジェクトとは別のクラスとして定義するようにしている
data class GetBookListResponse(val bookList: List<BookInfo>)

data class BookInfo(
    val id: Long,
    val title: String,
    val author: String,
    val isRental: Boolean
) {
    constructor(model: BookWithRental) : this(model.book.id, model.book.title, model.book.author, model.isRental)
}
