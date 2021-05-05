package com.example.demo.repository

import com.example.demo.entity.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int>

// CrudRepository：Spring Data JPA が用意した、テーブル操作の基本であるCRUDを行うインターフェース
// CrudRepository<操作するエンティティクラス、エンティティのIDの型>