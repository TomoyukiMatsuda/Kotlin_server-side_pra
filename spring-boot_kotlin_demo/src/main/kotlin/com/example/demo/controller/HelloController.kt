package com.example.demo.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

// @RestController：API機能のみ、view遷移せずメソッドの戻り値がそのままHTTPリクエストのレスポンスとなる
@Controller
class HelloController {
    // @RequestMapping()：リクエストを受け付けるパスを指定する
//    @RequestMapping("/hello")
//    fun index(): String {
//        return "ハローワールド！！！！"
//    }

    @GetMapping("/hello")
    fun index(model: Model): String {
        // Greeting という名前で ハローワールド！！ という文字列をviewに渡している
        model.addAttribute("Greeting", "ハローワールド！！")
        // テンプレート（HTMLファイル名）を返す
        return "hello"
    }
}