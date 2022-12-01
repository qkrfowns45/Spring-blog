package com.newbietop.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//인증이 안된 사용자들이 출입할 수 있는 경로를 /auth로만 허용
//그냥 주소가 /이면 index.jsp허용
//static이하에 있는 /js/~~ ,/css/~~ 등은 허용

@Controller
public class UserController {

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		
		return "user/loginForm";
	}
}
