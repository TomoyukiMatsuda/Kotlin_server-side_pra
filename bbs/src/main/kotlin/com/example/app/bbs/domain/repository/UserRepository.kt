package com.example.app.bbs.domain.repository

import com.example.app.bbs.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int> {
    // JPA の仕組みで findByフィールド名() と実装することで
    // フィールド名から検索が可能になるっぽい
    fun findByName(name: String) : User?
}