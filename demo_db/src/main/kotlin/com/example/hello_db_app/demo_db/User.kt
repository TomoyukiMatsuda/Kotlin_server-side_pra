package com.example.hello_db_app.demo_db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity // DBテーブルとなる、Userテーブル
data class User(
        @Id // プライマリーキーとなるフィールドに指定
        @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQLでいう AUTO_INCREMENT
        var id: Int = 0,
        var name: String = ""
)