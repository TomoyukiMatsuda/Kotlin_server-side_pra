package com.example.jissenkaihatu

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