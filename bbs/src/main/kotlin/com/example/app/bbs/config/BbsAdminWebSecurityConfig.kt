package com.example.app.bbs.config

import com.example.app.bbs.ADMIN_AUTHORITIES
import com.example.app.bbs.ADMIN_USER_PASSWORD
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

// クラス継承時の () はコンストラクタを呼び出すという指定：WebSecurityConfigurerAdapter()
@Configuration
@EnableWebSecurity
class BbsAdminWebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        // PasswordEncoder：パスワードを安全に処理するためにハッシュ化する仕組み
        return BCryptPasswordEncoder()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
            .withUser("admin")
            .password(passwordEncoder.encode(ADMIN_USER_PASSWORD))
            .authorities(ADMIN_AUTHORITIES)
    }
}