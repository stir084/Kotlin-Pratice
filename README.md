팁
www.youtube.com/watch?v=trK253PPUP8 유튜브 보고 따라한 내용이다.
기술 스펙 - kotlin + Springboot + gradle + H2 + intellij + jdk8
JDK란 "Java Development Kit (자바 개발 도구)"이라는 의미이고, SDK란 "Software Development Kit (소프트웨어 개발 도구)"의 약자
file -> setting -> compile -> build automatcilaty 체크하고 ctrl + shift + a 눌러서 registry 입력 후 compiler.automake.allow.when.app.running 체크를 하면 빌드 오토됨. 안그러면 계속 껏다켜야됨. 
mustache는 {{>페이지명}}적으면 페이지 삽입 {{변수명}}적으면 컨트롤러 model에서 던진 변수 사용

코틀린 기본 문법
fun main(src:Array){ 
	println("hello world") 
} 
코틀린 장점 - 널에 제약이 없다. 자바랑 100퍼 호환성. 안드로이드 앱 개발에 편하다 

프로젝트 구축 방법
-------------------아래 과정 실패함. 안됨---------------

인텔리제이에 kotlin + springboot 프로젝트를 실행시키기 위해 https://start.spring.io 에서 gradle + kotilin 선택 (스프링부트 버젼 선택할떄 snapshot이 붙으면 아직 개발단계) groupId 'com.wlProject' artifactId 'wl' 디펜던시 Web, JPA, H2, Devtools, Mustache 추가 generate해서 다운로드 받음 프로젝트 임폴트하고 임폴트 할때 use auto import 체크 의존성이 다 설치 될 때 까지 기다리고 main 폴더의 wl폴더 내에 wlApllication.kt kt파일 실행 

--------------------------------------------------------------------------

그래서 https://spring.io/guides/tutorials/spring-boot-kotlin/ 여기서 프로젝트 다운로드해서 오픈하니까 그냥 잘 실행됨 ---------------------------------------------------------------------------

package com.example.blog import org.springframework.stereotype.Controller 
import org.springframework.web.bind.annotation.RequestMapping 
import org.springframework.ui.Model 

@Controller 
class HelloControlerr { 
    @RequestMapping("/hello");
    fun index(model:Model){ 
    	println("index method") 
        return "footer" //하면 mustache의존성때문에 templates 폴더 밑 footer.mustache 실행 
    } 
    @GetMapping("/posts/{num}") 
    fun post(model:Model, @PathVariable num : Int):String{ 
    	//PathVariable 써주면 주소의 변수를 가져와줌 지우면 주소랑은 아무관련없어짐 
    	println("num:\t${num}") 
        return "footer" 
    }
}

//?를 붙이면 널도 허용한다는 뜻 ?없애면 null불가
 

데이터베이스 테이블 생성
resource폴더 아래 application.properties에 설정

spring.datasource.name=test
spring.datasource.initailize=false
spring.dataosurce.driverClassName=org.h2.Driver
spring.datasource.url=jdbc:h2:file:~/test
url의 test가 데이터베이스 이름이고 테이블을 위한 Entity를 위한 파일 하나를 만든다. 

jdbc:h2:file:~/test 경로중 ~/는 홈이란 표시 해당 위치는 어디있을까 cmd에서 start . 치면 폴더 열림 C:\Users\wnsgh라는 경로에있다. 
@Entity 
class junUser(//해당이름은 다른파일과 중복불가 
	var userId:String, 
	var password:String, 
	@Id @GeneratedValue var id:Long?=null)
http://localhost:8080/h2-console로 들어간다. 거기서 JDBC-URL이 프로퍼티파일에있는 url과 같아야한다.

그리고 test Connetion->connect클릭 그럼 좌측에 JUN_USER라는 테이블이 생겨있고 select * from jun_user로 불러올 수 있음.

 

데이터베이스 테이블 접근
JPA는 사용자 코드 ->레파지토리 인터페이스 -> 데이터인터페이스 ->h2,mysql 로 연결됨 

아래는 레파지토리 형식

interface junUserRepository: CrudRepository<junuser, long=""> { //이것 역시 다른 파일의 인터페이스명과 같으면 안됨.
    fun findByUserId(userId: String): User 
    fun findByPassword(password:String)//Entity 컬럼 앞 소문자를 대문자를 바꿔서 적어준다 } 
}
    
컨트롤러에서 save하는 방식  

@Autowired 
lateinit var repository:junUserRepository // lateinit 나중에 초기화함. 
    
@PostMapping("/sign") //데이터전송은 Post 페이지는 Get을 보통 씀. 
fun postSign(model: Model, @RequestParam(value = "id") userId:String, @RequestParam(value="password") password:String):String
{ 
   try { 
      val user = repository.save(junUser(userId, password)) 
      println(user.toString()) 
   } catch (e:Exception) { 
      e.printStackTrace() 
   } 
   return "footer" 
} 
