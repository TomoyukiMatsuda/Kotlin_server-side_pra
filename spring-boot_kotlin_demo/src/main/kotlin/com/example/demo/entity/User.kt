package com.example.demo.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity // このアノテーションがついたクラスと同名のテーブルがマッピングされる
data class User(
        @Id // 主キーに対応するフィールドに付与
        @GeneratedValue(strategy = GenerationType.IDENTITY) // DBの制約、ORMの関係？調べたい
        var id: Int = 0,
        var name: String = ""
)