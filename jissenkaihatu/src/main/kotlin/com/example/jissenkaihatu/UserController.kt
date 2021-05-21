package com.example.jissenkaihatu

import com.example.jissenkaihatu.database.*
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
    fun insert(@RequestBody request: InsertRequest): Response {
        val record = UserRecord(request.id, request.name, request.age, request.profile)
        // 作成したユーザーの 数とユーザーのオブジェクトを返してる
        return Response(userMapper.insert(record), userMapper.selectByPrimaryKey(request.id))
    }

    // ユーザー削除
    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id") id: Int): Response {
        val user = userMapper.selectByPrimaryKey(id)
        return Response(userMapper.deleteByPrimaryKey(id), user)
    }
}

// リクエスト
data class InsertRequest(val id: Int, val name: String, val age: Int, val profile: String)

// レスポンス
data class Response(val count: Int, val user: UserRecord?)