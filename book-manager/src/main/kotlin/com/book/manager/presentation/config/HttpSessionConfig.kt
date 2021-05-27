package com.book.manager.presentation.config

import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

// Redis でセッション管理を有効にするクラス
@EnableRedisHttpSession // SpringとRedisでのセッション管理を有効にしている
class HttpSessionConfig {

    // @Bean：付与した関数で返却したインスタンスがDIコンテナに登録され、そのインスタンスをDIでしようできるようになる。
    // 今回はspring-session-data-redisで内部的に利用される
    @Bean
    fun connectionFactory(): JedisConnectionFactory {
//        val redisStandaloneConfiguration = RedisStandaloneConfiguration().also {
//            it.hostName = "kotlin-redis"
//            it.port = 16379
//        }
//        return JedisConnectionFactory(redisStandaloneConfiguration)

        return JedisConnectionFactory()
    }
}