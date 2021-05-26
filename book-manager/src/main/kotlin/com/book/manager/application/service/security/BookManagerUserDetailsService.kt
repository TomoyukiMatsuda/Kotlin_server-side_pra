package com.book.manager.application.service.security

import com.book.manager.application.service.AuthenticationService
import com.book.manager.domain.enum.RoleType
import com.book.manager.domain.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class BookManagerUserDetailsService(
    private val authenticationService: AuthenticationService // コンストラクタインジェクション
) : UserDetailsService {

    /**
    ① 入力したユーザー名、パスワードをパラメータにログインAPIを実行
    ② loadUserByUsernameでユーザー名に該当するユーザー情報のUserDetails型のオブジェクトを取得
    ③ ②で取得したUserDetailsのgetPasswordで取得した値と、入力されたパスワードをBCryptでハッシュ化した値を比較
    ④ 認可が必要な場合、②で取得したUserDetailsのgetAuthoritiesで取得した権限のListに必要なものが含まれているか確認
     */

    // このloadUserByUsernameで取得したUserDetails型のオブジェクトを使用して、パスワードの比較や
    // 認可処理をフレームワーク側で実現してくれます。そしてその際に前述のパスワードの暗号化処理も使用されます。
    override fun loadUserByUsername(username: String): UserDetails? {
        // AuthenticationServiceの関数を呼んで、ユーザーを取得
        val user = authenticationService.findUser(username)
        return user?.let { BookManagerUserDetails(user) }
    }
}

// ログイン時に入力した値から取得したユーザーデータを格納し、認証処理で仕様されるものになる
data class BookManagerUserDetails(val id: Long, val email: String, val pass: String, val roleType: RoleType) : UserDetails {
    constructor(user: User) : this(user.id, user.email, user.password, user.roleType)

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        // 権限の取得。認可が必要なパスの場合、この関数で取得した権限の情報でチェックされる
        return AuthorityUtils.createAuthorityList(this.roleType.toString())
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return this.email
    }

    override fun getPassword(): String {
        return this.pass
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }
}