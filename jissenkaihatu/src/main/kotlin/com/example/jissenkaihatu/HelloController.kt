package com.example.jissenkaihatu

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class HelloController {
    @GetMapping("index/{name}")
    fun index(model: Model): Message {
        // この記述によりThymeleafで message（第一引数） という名前で第2引数を利用できる
        model.addAttribute("message", "Hello ワールド!!")
        return Message("JSONで返す")
    }

    data class Message(val message: String)
}