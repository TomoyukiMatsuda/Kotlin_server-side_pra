package com.example.app.bbs.app.controller

import com.example.app.bbs.app.request.ArticleRequest
import com.example.app.bbs.domain.entity.Article
import com.example.app.bbs.domain.repository.ArticleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
class ArticleController {
    // 依存性注入
    @Autowired
    lateinit var articleRepository: ArticleRepository

    // 新規投稿
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

    // 一覧表示
    @GetMapping("/")
    fun getArticleList(model: Model): String {
        model.addAttribute("articles", articleRepository.findAll())
        // viewファイル名を返す
        return "index"
    }

    // 投稿編集
    @GetMapping("/edit/{id}") // urlを指定しつつ{id}でリクエストパラメータを受け取る
    fun getArticleEdit(@PathVariable id: Int, model: Model): String {
        // @PathVariable：@GetMapping("/edit/{id}") で指定したパスを受け取ることを宣言するアノテーション
        return if(articleRepository.existsById(id)) {
            // 引数idをもつ投稿があれば 投稿データを取得
            model.addAttribute("article", articleRepository.findById(id))
            // viewテンプレート edit に遷移
            "edit"
        } else {
            // 引数idをもつ投稿がなければ
            "redirect:/"
        }
    }

    @PostMapping("/update")
    fun updateArticle(articleRequest: ArticleRequest): String {
        if (!articleRepository.existsById(articleRequest.id)) {
            // id の投稿が存在しない時
            return "redirect:/"
        }

        val article = articleRepository.findById(articleRequest.id).get()

        if (articleRequest.articleKey != article.articleKey) {
            // articleKeyが一致しない時
            return "redirect:/edit/${articleRequest.id}"
        }

        article.name = articleRequest.name
        article.title = articleRequest.title
        article.contents = articleRequest.contents
        article.updateAt = Date()

        articleRepository.save(article)
        return "redirect:/"
    }
}