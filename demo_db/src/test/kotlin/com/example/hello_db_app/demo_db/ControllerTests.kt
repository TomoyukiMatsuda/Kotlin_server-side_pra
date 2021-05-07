package com.example.hello_db_app.demo_db

import com.example.hello_db_app.demo_db.MainController
import com.example.hello_db_app.demo_db.User
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest
class ControllerTests {
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var target: MainController

    @Before // テストメソッド（@Test fun()）が実行される前に呼び出される宣言、今回はモックを作成
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(target).build()
    }

    @Test // テストメソッドであることを宣言
    fun getAllUsersTest() {
        mockMvc.perform(
                get("/all")
        )
                .andExpect(status().isOk) // レスポンスコードが200であることをテスト
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // レスポンスの形式がJSONであることをテスト
    }
}