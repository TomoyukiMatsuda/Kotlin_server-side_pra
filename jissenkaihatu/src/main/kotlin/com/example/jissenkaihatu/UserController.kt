package com.example.jissenkaihatu

import com.example.jissenkaihatu.database.UserMapper
import com.example.jissenkaihatu.database.UserRecord
import com.example.jissenkaihatu.database.selectByPrimaryKey
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Suppress("SpringJavaInjectionPointsAutowiringInspection") // 警告を無視するためのアノテーション
@RestController
@RequestMapping("user")
class UserController(val userMapper: UserMapper) {
    @GetMapping("/select/{id}")
    fun select(@PathVariable("id") id: Int): UserRecord? {
        return userMapper.selectByPrimaryKey(id)
    }
}