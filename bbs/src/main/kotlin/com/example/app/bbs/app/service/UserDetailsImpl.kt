package com.example.app.bbs.app.service

import com.example.app.bbs.domain.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(argUser: User) : UserDetails {

    val user: User = argUser

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        // TODO: 仮実装
        // ユーザーに付与された権限を返す
        return mutableListOf()
    }

    override fun isEnabled(): Boolean {
        // ユーザーが有効か無効化を示す、false: 無効 / true: 有効
        return true
    }

    override fun getUsername(): String {
        return user.name
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun isCredentialsNonExpired(): Boolean {
        // ユーザーの資格情報（パスワード）の有効期限が切れているかどうかを示す
        // パスワードに有効期限を儲けたいときに使用
        return true
    }

    override fun isAccountNonExpired(): Boolean {
        // ユーザーアカウントの有効期限が切れているかどうかを示す
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        // ユーザーがロックされているかロック解除されているかを示す
        // true: ロック解除、false: ロック状態
        return true
    }
}