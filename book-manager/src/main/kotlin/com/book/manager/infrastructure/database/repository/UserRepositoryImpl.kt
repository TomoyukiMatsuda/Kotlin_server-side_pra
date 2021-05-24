package com.book.manager.infrastructure.database.repository

import com.book.manager.domain.model.User
import com.book.manager.domain.repository.UserRepository
import com.book.manager.infrastructure.database.mapper.UserDynamicSqlSupport
import com.book.manager.infrastructure.database.mapper.UserMapper
import com.book.manager.infrastructure.database.mapper.selectOne
import com.book.manager.infrastructure.database.record.UserRecord
import org.mybatis.dynamic.sql.SqlBuilder.isEqualTo
import org.springframework.stereotype.Repository

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Repository // DI対象を示すアノテーション
class UserRepositoryImpl(
    private val mapper: UserMapper
) : UserRepository {

    override fun find(email: String): User? {
        val record = mapper.selectOne {
            // メールアドレスから一致するデータを取得
            where(UserDynamicSqlSupport.User.email, isEqualTo(email))
        }
        // 取得した record をUserクラスに変換して返す
        return record?.let { toModel(it) }
    }

    private fun toModel(record: UserRecord): User {
        return User(
            record.id!!,
            record.email!!,
            record.password!!,
            record.name!!,
            record.roleType!!
        )
    }
}