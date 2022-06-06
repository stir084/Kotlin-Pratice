package com.example.blog

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class junUser(
    var userId:String,
    var password:String,
    @Id @GeneratedValue var id:Long?=null) //Id는 자동 id생성
