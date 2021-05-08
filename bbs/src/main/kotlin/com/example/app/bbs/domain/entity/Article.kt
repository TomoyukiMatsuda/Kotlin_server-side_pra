package com.example.app.bbs.domain.entity

import java.util.*
import javax.persistence.*

@Entity
data class Article(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int = 0,
        var name: String = "",
        var title: String = "",
        var contents: String = "",
        @Column(name = "article_key")
        var articleKey: String = "",
        @Column(name = "register_at")
        var registerAt: String = "",
        @Column(name = "update_at")
        var updateAt: Date = Date(),
        @Column(name = "user_id")
        var userId: Int? = null
)

// @Column(name = "")はクラスのプロパティ名とテーブルのフィールド名が異なる時に指定する