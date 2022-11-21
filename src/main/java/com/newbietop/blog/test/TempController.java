package com.newbietop.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempController {

	@GetMapping("/temp/home")
	public String home(){
		
		System.out.println("temp/home");
		return "/home.html";
		
	}
	
	@GetMapping("/temp/jsp")
	public String jsp(){
		
		System.out.println("test.jsp");
		return "test";
		
	}
}
