package com.book.manager.infrastructure.database.repository

import com.book.manager.domain.model.Book
import com.book.manager.domain.model.BookWithRental
import com.book.manager.domain.model.Rental
import com.book.manager.domain.repository.BookRepository
import com.book.manager.infrastructure.database.mapper.BookMapper
// import文を追加しているのは、insert関数がBookMapperクラスとBookWithRentalMapperExtentions.ktの
// 両方に存在し、import文を書かないとBookMapperクラスのほうの関数が呼ばれ、コンパイルエラーになってしまうため
import com.book.manager.infrastructure.database.mapper.insert
import com.book.manager.infrastructure.database.mapper.custom.BookWithRentalMapper
import com.book.manager.infrastructure.database.mapper.custom.select
import com.book.manager.infrastructure.database.mapper.custom.selectByPrimaryKey
import com.book.manager.infrastructure.database.mapper.updateByPrimaryKeySelective
import com.book.manager.infrastructure.database.record.BookRecord
import com.book.manager.infrastructure.database.record.custom.BookWithRentalRecord
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Repository  // ID のクラスであることを示すアノテーション
class BookRepositoryImpl(
    private val bookWithRentalMapper: BookWithRentalMapper,
    private val bookMapper: BookMapper
) : BookRepository {

    override fun findAllWithRental(): List<BookWithRental> {
        // map : で加工したListを返す
        return bookWithRentalMapper.select().map { toModel(it) }
    }

    override fun findWithRental(id: Long): BookWithRental? {
        return bookWithRentalMapper.selectByPrimaryKey(id)?.let { toModel(it) }
    }

    override fun register(book: Book) {
        // 登録処理を実装
        // BookMapperExt の fun BookMapper.insert(record: BookRecord) を呼んでいる
        bookMapper.insert(toRecord(book))
    }

    override fun update(id: Long, title: String?, author: String?, releaseDate: LocalDate?) {
        // 更新処理 id以外の値がnull許容となっているのは null であればその値は更新しないようにしているため（だからselective
        bookMapper.updateByPrimaryKeySelective(BookRecord(id, title, author, releaseDate))
    }

    private fun toRecord(model: Book): BookRecord {
        // DBに登録するために、BookクラスをBookRecordクラスへ変換する処理
        // BookRecord インスタンスを生成して返すだけ
        return BookRecord(model.id, model.title, model.author, model.releaseDate)
    }

    private fun toModel(record: BookWithRentalRecord): BookWithRental {
        // DBから取得した レコードを
        // model にあるBookWithRental に変換して利用しやすくしている
        val book = Book(
            record.id!!,
            record.title!!,
            record.author!!,
            record.releaseDate!!
        )
        val rental = record.userId?.let {
            Rental(
                record.id,
                record.userId!!,
                record.rentalDateTime!!,
                record.returnDeadline!!
            )
        }
        return BookWithRental(book, rental)
    }
}