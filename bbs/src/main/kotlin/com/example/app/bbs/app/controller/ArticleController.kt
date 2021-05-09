package com.example.app.bbs.app.controller

import com.example.app.bbs.app.request.ArticleRequest
import com.example.app.bbs.domain.entity.Article
import com.example.app.bbs.domain.repository.ArticleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*

@Controller
class ArticleController {

    val MESSAGE_REGISTER_NORMAL = "正常に投稿できました。"
    val MESSAGE_ARTICLE_DOES_NOT_EXISTS = "対象の記事が見つかりませんでした。"
    val MESSAGE_UPDATE_NORMAL = "正常に更新しました。"
    val MESSAGE_ARTICLE_KEY_UNMATCH = "投稿KEYが一致しません。"
    val MESSAGE_DELETE_NORMAL = "正常に削除しました。"
    val ALERT_CLASS_ERROR = "alert-error"

    // 依存性注入
    @Autowired
    lateinit var articleRepository: ArticleRepository

    // 新規投稿
    @PostMapping("/")
    fun registerArticle(@Validated @ModelAttribute articleRequest: ArticleRequest,
                        result: BindingResult,
                        redirectAttributes: RedirectAttributes): String {
        // @Validated：バリデーションすることを宣言
        // result: BindingResultでバリデーションの結果を受け取る（必ずバリデーションしたいパラメータの次の引数にする、この順番で無いとバリデーション正しくされない

        if (result.hasErrors()) {
            // バリデーション結果にエラーがあったら
            redirectAttributes.addFlashAttribute("errors", result)
            redirectAttributes.addFlashAttribute("request", articleRequest)

            return "redirect:/"
        }


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
        // addFlashAttribute(名前, データ) でデータをセットしている
        redirectAttributes.addFlashAttribute("message", MESSAGE_REGISTER_NORMAL)

        // 「/」はリダイレクト先
        return "redirect:/"
    }

    // 一覧表示
    @GetMapping("/")
    fun getArticleList(@ModelAttribute articleRequest: ArticleRequest, model: Model): String {
        if (model.containsAttribute("errors")) {
            // registerArticle() でセットされた「errors」があったら
            val key: String = BindingResult.MODEL_KEY_PREFIX + "articleRequest"
            model.addAttribute(key, model.asMap()["errors"])
        }

        if (model.containsAttribute("request")) {
            // registerArticle() でセットされた「request」があったら属性をセットし直している？
            model.addAttribute("articleRequest", model.asMap()["request"])
        }

        model.addAttribute("articles", articleRepository.findAll())
        // viewファイル名を返す
        return "index"
    }

    // 投稿編集
    @GetMapping("/edit/{id}") // urlを指定しつつ{id}でリクエストパラメータを受け取る
    fun getArticleEdit(@PathVariable id: Int,
                       model: Model,
                       redirectAttributes: RedirectAttributes
    ): String {
        // @PathVariable：@GetMapping("/edit/{id}") で指定したパスを受け取ることを宣言するアノテーション
        return if(articleRepository.existsById(id)) {
            // 引数idをもつ投稿があれば 投稿データを取得
            model.addAttribute("article", articleRepository.findById(id))
            // viewテンプレート edit に遷移
            "edit"
        } else {
            // 引数idをもつ投稿がなければ
            redirectAttributes.addFlashAttribute("message",
                    MESSAGE_ARTICLE_DOES_NOT_EXISTS)
            redirectAttributes.addFlashAttribute("alert_class",
                    ALERT_CLASS_ERROR)

            "redirect:/"
        }
    }

    @PostMapping("/update")
    fun updateArticle(articleRequest: ArticleRequest, redirectAttributes: RedirectAttributes): String {
        if (!articleRepository.existsById(articleRequest.id)) {
            // id の投稿が存在しない時
            redirectAttributes.addFlashAttribute("message",
                    MESSAGE_ARTICLE_DOES_NOT_EXISTS)
            redirectAttributes.addFlashAttribute("alert_class",
                    ALERT_CLASS_ERROR)
            return "redirect:/"
        }

        val article = articleRepository.findById(articleRequest.id).get()

        if (articleRequest.articleKey != article.articleKey) {
            // articleKeyが一致しない時
            redirectAttributes.addFlashAttribute("message",
                    MESSAGE_ARTICLE_KEY_UNMATCH)
            redirectAttributes.addFlashAttribute("alert_class",
                    ALERT_CLASS_ERROR)
            return "redirect:/edit/${articleRequest.id}"
        }

        article.name = articleRequest.name
        article.title = articleRequest.title
        article.contents = articleRequest.contents
        article.updateAt = Date()

        articleRepository.save(article)

        redirectAttributes.addFlashAttribute("message",
                MESSAGE_UPDATE_NORMAL)

        return "redirect:/"
    }

    @GetMapping("/delete/confirm/{id}")
    fun articleDeleteConfirm(@PathVariable id: Int,
                             model: Model,
                             redirectAttributes: RedirectAttributes
    ): String {

        return if (articleRepository.existsById(id)) {

            model.addAttribute("article", articleRepository.findById(id).get())

            "delete_confirm"
        } else {
            redirectAttributes.addFlashAttribute("message",
                    MESSAGE_ARTICLE_DOES_NOT_EXISTS)
            redirectAttributes.addFlashAttribute("alert_class",
                    ALERT_CLASS_ERROR)
            "redirect:/"
        }
    }

    @PostMapping("/delete")
    fun deleteArticle(articleRequest: ArticleRequest, redirectAttributes: RedirectAttributes): String {
        if (!articleRepository.existsById(articleRequest.id)) {
            // id の投稿が存在しない時
            redirectAttributes.addFlashAttribute("message",
                    MESSAGE_ARTICLE_DOES_NOT_EXISTS)
            redirectAttributes.addFlashAttribute("alert_class",
                    ALERT_CLASS_ERROR)

            return "redirect:/"
        }

        // id から投稿を取得
        val article = articleRepository.findById(articleRequest.id).get()

        if (articleRequest.articleKey != article.articleKey) {
            // key が一致しない時
            redirectAttributes.addFlashAttribute("message",
                    MESSAGE_ARTICLE_KEY_UNMATCH)
            redirectAttributes.addFlashAttribute("alert_class",
                    ALERT_CLASS_ERROR)
            return "redirect:/delete/confirm/${article.id}"
        }

        articleRepository.deleteById(articleRequest.id)
        redirectAttributes.addFlashAttribute("message",
                    MESSAGE_DELETE_NORMAL)

        return "redirect:/"
    }
}