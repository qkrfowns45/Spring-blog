package com.newbietop.blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.newbietop.blog.config.auth.PrincipalDetail;

@Controller
public class BoardController {

	@GetMapping({"","/"})
	public String index(@AuthenticationPrincipal PrincipalDetail principal) { //컨트롤러에서 세션을 어떻게 찾는지?
		
		System.out.println("로그인 사용자 아이디 : " +principal.getUsername());
		return  "index";
	}
}
