package com.example.jissenkaihatu

import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
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


// TODO: フィールドインジェクション
// コンストラクタインジェクションによりDIされて、Greeterの実装クラスで@Componentアノテーションが付いたクラス、
//つまりGreeterImplがインジェクションされる
@RestController
class GreeterController {
    @Autowired
    @Qualifier("US")
    private lateinit var greeter: Greeter

    @GetMapping("/hello/{name}")
    fun helloByService(@PathVariable("name") name: String): Message {
        val message = greeter.hello(name)
        return Message(message)
    }
}

interface Greeter {
    fun hello(name: String): String
}

// interface Greeter の実装クラスが JP と US ２つ存在する
@Component("JP")
class JPGreeterImpl : Greeter {
    override fun hello(name: String): String = "ハロー $name"
}

@Component("US")
class USGreeterImpl : Greeter {
    override fun hello(name: String): String = "Hello $name"
}