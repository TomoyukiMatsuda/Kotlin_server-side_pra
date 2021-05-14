package com.example.app.bbs.app.service

import com.example.app.bbs.domain.entity.User
import com.example.app.bbs.domain.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

// サービス層を示すアノテーション、Springではビジネスロジックを記述する
// Controller には日jネスロジックは書かず、Service層を利用するのが推奨されている
@Service
class UserDetailsServiceImpl : UserDetailsService { // UserDetailsService：ユーザー固有のデータを読み込むためのインターフェース

    @Autowired // DI
    lateinit var userRepository: UserRepository

    // ユーザー名を検索するメソッド
    override fun loadUserByUsername(username: String?): UserDetails {
        var user: User? = null
        if (username != null) {
            user = userRepository.findByName(username)
        }

        if (user == null) {
            throw UsernameNotFoundException(username)
        }

        return UserDetailsImpl(user)
    }
}