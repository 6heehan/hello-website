package com.example.demo.main.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.components.MailComponents;

import lombok.RequiredArgsConstructor;

// MainPage 클래스를 만드는 목적
// 매핑하기 위해서
// 주소와 (논리적인주소 , 인터넷주소) 물리적인파일 ( 프로그래밍 하는 파일 ) 매핑

// http://www.naver.com/new/list.do
// 하나의 주소에 대해서
// 어디서 매핑? 누가 매핑 ?
// 후보군 ? 클래스, 속성, 메소드

@RequiredArgsConstructor
@Controller
public class MainController {

	private final MailComponents mailComponents;
	
	@RequestMapping("/")
	public String index() {
		//mailComponents.sendMailTest();
		//String email = "6heehan@gmail.com";
		//String subject = "안녕하세요. 제로베이스 입니다. ";
		//String text = "<p>안녕하세요.</p><p>반갑습니다.</p>";
		//mailComponents.sendMail(email, subject, text);
		return "index";
	}
	
	// 스프링 -> MVC (View => 템플릿엔진 화면에 출력 (html))
	// .NET -> MVC (Veiw => 출력)
	// python django -> MTV (Template => 화면 출력)
	// 백엔드과정 -> V (화면에 보여지는 부분) => 프론트엔드 과정
	// V -> HTML ==> 웹페이지
	// V -> json ==> API
	
	// request -> WEB -> SERVER
	// response -> SERVER -> WEB
	@RequestMapping("/hello")
	public void hello(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter printWriter = response.getWriter();
		
		String s = "hello \r\n fastlms website!!!";
		String s1 = "<p>hello</p> <p>fastlms website!!!!!</p>";
		String msg = "<html>" +
				"<head>" +
				"<meta charset=\"UTF-8\">" +
				"</head>" +
				"<body>" +
				"<p>hello</p>" +
				"<p>Website!!!</p>" +
				"<p>안녕하세요.</p>" +
				"</body>" +
				"</html>";
				
		//return "hello";
		
		printWriter.write(msg);
		printWriter.close();
		//return s1;
	}
	
	@RequestMapping("/hi")
	public String hi() {
		return "hi";
	}
}
