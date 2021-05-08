package com.example.app.bbs.unit.controller

import com.example.app.bbs.app.controller.ArticleController
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest
class articleControllerTests {
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var target: ArticleController

    @Before
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(target).build()
    }

    @Test
    fun registerArticleTest() {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/")
                        .param("name", "test")
                        .param("title", "test")
                        .param("contents", "test")
                        .param("articleKey", "test")
        )
                .andExpect(status().is3xxRedirection) // リダイレクトを促すレスポンスコード
                .andExpect(view().name("redirect:/"))

                //.andExpect(content().string("Saved")) // レスポンスの中身が「Saved」
    }

    @Test
    fun getArticleListTest() {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/")
        )
                .andExpect(status().isOk) // 200
                .andExpect(model().attributeExists("articles"))
                .andExpect(view().name("index"))
    }
}