package com.example.app.bbs.config

import com.example.app.bbs.ADMIN_AUTHORITIES
import com.example.app.bbs.ADMIN_USER_PASSWORD
import com.example.app.bbs.app.service.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.util.AntPathMatcher

// クラス継承時の () はコンストラクタを呼び出すという指定：WebSecurityConfigurerAdapter()
@Configuration
@EnableWebSecurity
class BbsAdminWebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var userDetailsService: UserDetailsServiceImpl

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        // PasswordEncoder：パスワードを安全に処理するためにハッシュ化する仕組み
        return BCryptPasswordEncoder()
    }

    // WebSecurity：セキュリティを無視する設定を指定する
    override fun configure(web: WebSecurity?) {
        // このように設定した物はセキュリティ設定を無視される
        web?.ignoring()?.antMatchers(
            "/favicon.ico",
            "/css/**",
            "/js/**"
        )
    }

    // HttpSecurity：特定の http 要求に対してセキュリティの設定を行う
    override fun configure(http: HttpSecurity?) {
        http ?: return
        // 許可の設定 「/admin」 と 「/admin/」配下は認証が必要
        // それ以外は全てアクセス許可
        http.authorizeRequests()
                .antMatchers("/admin", "/admin/*").authenticated()
                .anyRequest().permitAll()

        // ログイン設定（デフォルトの設定を用いる）
        http.formLogin()

        // ログアウト
        http.logout()
            .logoutRequestMatcher(AntPathRequestMatcher("/user/logout**"))
            .logoutSuccessUrl("/")
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        // TODO: DBからユーザー情報読み出すのがうまくいかない
        auth.inMemoryAuthentication()
            .withUser("admin")
            .password("\$2a\$10\$zkWc793o2fA1eHIDpudpKuCY9HBhWIz2dSTvvERSdsajfq7uybja2")
            .authorities("ROLE_ADMIN")

        auth.userDetailsService(userDetailsService)
    }
}