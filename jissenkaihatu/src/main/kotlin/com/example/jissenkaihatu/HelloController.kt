package com.example.jissenkaihatu

import org.apache.coyote.Response
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*


data class Message(val message: String)
data class Request(val name: String)

@RestController
@RequestMapping("/")
class HelloController {
    @PostMapping("index")
    fun index(@RequestBody request: Request): Message {
        return Message("ハロー ${request.name}")
    }
}


// TODO: 「第２部 3 Spring FrameworkのDIを使用する」 からやる！！
// コンストラクタインジェクションによりDIされて、クラス内でgreeterが利用できる
@RestController
class GreeterController(private val greeter: Greeter) {
    @GetMapping("/hello/{name}")
    fun helloByService(@PathVariable("name") name: String): Message {
        val message = greeter.hello(name)
        return Message(message)
    }
}

interface Greeter {
    fun hello(name: String): String
}

@Component // DIの対象であることを表す、DIしたオブジェクトはシングルトンになる
class GreeterImpl : Greeter {
    override fun hello(name: String): String = "ハロー $name"
}