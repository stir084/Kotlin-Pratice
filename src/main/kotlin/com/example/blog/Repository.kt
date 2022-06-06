package com.example.blog

import org.springframework.data.repository.CrudRepository

interface junUserRepository: CrudRepository<junUser, Long> {
    fun findByUserId(userId: String)
    fun findByPassword(password:String)//Entity 컬럼 앞 소문자를 대문자를 바꿔서 적어준다
}