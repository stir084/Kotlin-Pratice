package com.example.blog

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.lang.Exception
import javax.servlet.http.HttpSession

@Controller
class HelloController {

    @Autowired
    lateinit var repository:junUserRepository // lateinit 나중에 초기화함.

    @Autowired
    lateinit var repository2:UserRepository // lateinit 나중에 초기화함.


    @GetMapping("/test")
    fun index(model:Model):String{
        println("idddd") //에러는 나도 실행은 됨
        var response:String = "뻐큉"
        model.addAttribute("title", "ㅗㅗㅗㅗ")
        return "footer"
    }

    @GetMapping("/posts/{num}")
    fun post(model:Model, @PathVariable num : Int):String{
        println("num:\t${num}") //에러는 나도 실행은 됨
        var name:Int = 3
        println(name);

        return "footer"
    }

    @PostMapping("/sign") //데이터전송은 Post 페이지는 Get을 보통 씀.
    fun postSign(model: Model, @RequestParam(value = "id") userId:String, @RequestParam(value="password") password:String):String{
        try {
            val user = repository.save(junUser(userId, password))

            println(user.toString())
            model.addAttribute("title", "ㅗㅗㅗㅗ")
        } catch (e:Exception) {
            e.printStackTrace()
        }
        return "footer"
    }

    @GetMapping("{site}")
    fun post(model:Model, @PathVariable site : String):String{
        var di:String = "/"
        println("하하"+site)
        if(site == "login"){
            di = "/login"
        }

        return di
    }

    @PostMapping("/loginVerify")
    fun postLogin(model:Model, session: HttpSession, @RequestParam(value = "id") userId:String, @RequestParam(value = "password") password: String): String {
        try {
            val passw = password
            val db_user = repository.findByUserId("123123")
            val db_user2= repository2.findByLogin("tttt")
           // println(db_user2.firstname)
           // println(db_user.userId)
            println("크ㅡㅋ")
            if(db_user != null){
                //val db_pass = db_user.password
                if(passw.equals("111")){
                    //session.setAttribute("userId", db_user.userId)
                    return "welcome"
                }
                else {
                    model.addAttribute("title", "login")
                    return "login"
                }
            }
        }catch (e:Exception) {
            e.printStackTrace()
        }
        return "welcome"
    }
}