package com.example.demo.controller

import com.example.demo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

// @Controller：テンプレート（HTML）を返したい場合
// @RestController：APIのエントリポイントにしたい場合（メソッドの戻り値がレスポンスボティになる）
@Controller
class MainController {
    @Autowired // DIするクラスを指定するために使う。今回ではDBからのデータ取得を行うUserRepositoryのインスタンスが注入される
    lateinit var userRepository: UserRepository

    // 「/」に対してGETメソッドでリクエストが飛んできた場合、あのテーとされたメソッド（今回だとshowUsers())が呼び出される
    @GetMapping("/")
    fun showUsers(model: Model): String {
        // 全ユーザーを取得するメソッド
        val users = userRepository.findAll()
        // テンプレートに値を渡したい時は、メソッドの引数にModelを指定
        model.addAttribute("users", users) // addAttribute(テンプレートでの変数名, 値）

        // テンプレートに"templates/index.html"を指定
        // @Controllerがついたコントローラー内のメソッドの返り値に文字列を指定すると、そのファイル名のテンプレートをViewに指定します。
        return "index"
    }

    // 追加ページリクエスト処理："/add"にGETメソッドで来たリクエストをshowAddPage()メソッドにマッピング
    @GetMapping("/add")
    fun showAddPage(): String {
        // テンプレートに"add.html"を指定
        return "add"
    }
}