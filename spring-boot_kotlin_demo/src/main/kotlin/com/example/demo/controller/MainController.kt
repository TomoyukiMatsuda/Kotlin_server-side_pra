package com.example.demo.controller

import com.example.demo.entity.User
import com.example.demo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

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

    // @RequestParam：指定した引数がリクエストパラメータであることを示す（フォーム入力文字列がnameという変数に入る）
    @PostMapping("/add")
    fun addNewUser(@RequestParam name: String): String {
        // ここではフィールドnameがaddNewUser()に渡されたnameであるようなインスタンスを生成し、DBに保存しています。
        // 0になっている箇所はIDで、ここに既存のIDを入れてしまうとUpdateになってしまうので、存在しない0を渡し、Createするようにしています。
        userRepository.save(User(0, name))
        // "/" にリダイレクトさせるという意味
        return "redirect:/"
    }
}