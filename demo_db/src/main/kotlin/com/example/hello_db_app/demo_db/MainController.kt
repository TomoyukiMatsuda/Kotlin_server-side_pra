package com.example.hello_db_app.demo_db

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController {
    @Autowired // 依存性注入 該当するクラスのインスタンスを自動で new してくれる（UserRepositoryのインスタンス
    lateinit var userRepository: UserRepository

    @PostMapping("/add")
    fun addNewUser(@RequestParam name: String): String { //引数がリクエストパラメーターであることを宣言する
        userRepository.save(User(0, name))
        return "Saved"
    }

    @GetMapping("/all")
    fun getAllUsers(): Iterable<User>? {
        // 全件取得
        return userRepository.findAll()
    }

    @PostMapping("/update")
    fun updateUser(@RequestParam id: Int, name: String): String {
        userRepository.save(User(id, name))
        return "Updated"
    }

    @PostMapping("/delete")
    fun deleteUser(@RequestParam id: Int): String {
        // 引数で受け取ったidのユーザーが存在すれば？
        return if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            "Deleted"
        } else {
            "Non User"
        }
    }
}