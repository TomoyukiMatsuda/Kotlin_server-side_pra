package com.example.hello_db_app.demo_db

import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int>
// CrudRepository：DBテーブルに対する基本操作であるCreate, Read, Update, Delete を実現するためのクラス