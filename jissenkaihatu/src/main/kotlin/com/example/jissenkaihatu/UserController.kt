package com.example.jissenkaihatu

import com.example.jissenkaihatu.database.UserMapper
import com.example.jissenkaihatu.database.UserRecord
import com.example.jissenkaihatu.database.insert
import com.example.jissenkaihatu.database.selectByPrimaryKey
import org.springframework.web.bind.annotation.*

@Suppress("SpringJavaInjectionPointsAutowiringInspection") // 警告を無視するためのアノテーション
@RestController
@RequestMapping("user")
class UserController(val userMapper: UserMapper) { // Mapperオブジェクト（UserMapper）をDIしている、コンストラクタインジェクション
    // ユーザーを取得
    @GetMapping("/select/{id}")
    fun select(@PathVariable("id") id: Int): UserRecord? {
        return userMapper.selectByPrimaryKey(id)
    }

    // ユーザー登録
    @PostMapping("/insert")
    fun insert(@RequestBody request: InsertRequest): InsertResponse {
        val record = UserRecord(request.id, request.name, request.age, request.profile)
        // 作成したユーザーの 数とユーザーのオブジェクトを返してる
        return InsertResponse(userMapper.insert(record), userMapper.selectByPrimaryKey(request.id))
    }
}

// リクエスト
data class InsertRequest(val id: Int, val name: String, val age: Int, val profile: String)

// レスポンス
data class InsertResponse(val count: Int, val user: UserRecord?)