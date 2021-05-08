package com.example.app.bbs.app.controller

import com.example.app.bbs.app.request.ArticleRequest
import com.example.app.bbs.domain.entity.Article
import com.example.app.bbs.domain.repository.ArticleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class ArticleController {
    // 依存性注入
    @Autowired
    lateinit var articleRepository: ArticleRepository

    @PostMapping("/")
    fun registerArticle(@ModelAttribute articleRequest: ArticleRequest): String {
        // @ModelAttribute：引数で指定すると対象の引数をリクエストパラメータと紐付けできる（name,titleなどにアクセスできる
        articleRepository.save(
                Article(
                        articleRequest.id,
                        articleRequest.name,
                        articleRequest.title,
                        articleRequest.contents,
                        articleRequest.articleKey,
                ),
        )
        // 「/」はリダイレクト先
        return "redirect:/"
    }

    @GetMapping("/")
    fun getArticleList(model: Model): String {
        model.addAttribute("articles", articleRepository.findAll())
        // viewファイル名を返す
        return "index"
    }
}