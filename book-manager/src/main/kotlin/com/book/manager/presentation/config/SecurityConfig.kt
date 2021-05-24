package com.book.manager.presentation.config

import com.book.manager.application.service.AuthenticationService
import com.book.manager.application.service.security.BookManagerUserDetailsService
import com.book.manager.domain.enum.RoleType
import com.book.manager.presentation.handler.BookManagerAccessDeniedHandler
import com.book.manager.presentation.handler.BookManagerAuthenticationEntryPoint
import com.book.manager.presentation.handler.BookManagerAuthenticationFailureHandler
import com.book.manager.presentation.handler.BookManagerAuthenticationSuccessHandler
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableWebSecurity
class SecurityConfig(private val authenticationService: AuthenticationService) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .mvcMatchers("/login").permitAll() // ログインAPIに対して未認証ユーザーを含む全てのアクセスを許可
            .mvcMatchers("/admin/**").hasAuthority(RoleType.ADMIN.toString()) // 「/admin」から始まるAPIに対して管理者権限のユーザーのみアクセス許可
            .anyRequest().authenticated()  // 上記「/login」「/admin/**」以外のAPIは認証すみユーザーのみアクセスを許可
            .and()
            .csrf().disable()
            .formLogin() // フォームログイン（ユーザー名、パスワードでのログイン）を有効化
            .loginProcessingUrl("/login") // loginProcessingUrl でログインAPIのパスを「/login」に設定
            .usernameParameter("email") // ログインAPIに渡すユーザー名のパラメータ名をemailに設定
            .passwordParameter("pass") // ログインAPIに渡すパスワードのパラメータ名をpassに設定
            .successHandler(BookManagerAuthenticationSuccessHandler()) // 認証成功時に実行するハンドラーを設定
            .failureHandler(BookManagerAuthenticationFailureHandler()) // 認証失敗時に実行するハンドラーを設定
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(BookManagerAuthenticationEntryPoint()) // 未認証時のハンドラーを設定
            .accessDeniedHandler(BookManagerAccessDeniedHandler()) // 認可失敗時のハンドラーを設定
            .and()
            .cors().configurationSource(corsConfigurationSource()) // CORS に関する設定をしている
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(BookManagerUserDetailsService(authenticationService)) // 認証処理を実行するクラスの指定
            .passwordEncoder(BCryptPasswordEncoder()) // パスワードの暗号化アルゴリズムをBCryptに指定
    }

    // CORS に関する設定をしている
    private fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL)
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL)
        corsConfiguration.addAllowedOrigin("http://localhost:8081") // アクセス元のドメインを許可（環境に応じて変更が必要）
        corsConfiguration.allowCredentials = true

        val corsConfigurationSource = UrlBasedCorsConfigurationSource()
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration)

        return corsConfigurationSource
    }
}