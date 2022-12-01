package com.newbietop.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.newbietop.blog.dto.ResponseDto;
import com.newbietop.blog.model.RoleType;
import com.newbietop.blog.model.User;
import com.newbietop.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;

	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) { // username,password,email외에 나머지는 알아서 들어간다.
		System.out.println("UserApiController : save호출됨");
		//실제로 DB에 insert를 하고 아래에서 return한다.
		user.setRole(RoleType.USER);
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	//스프링 시큐리티 이용해서 로그인!!(암호화가 필요하기 때문에 로그인 개념만 챙기기)
	/*
	 * @PostMapping("/api/user/login") public ResponseDto<Integer>
	 * login(@RequestBody User user, HttpSession session){
	 * System.out.println("UserApiController : login호출됨"); User principal =
	 * userService.로그인(user); //principal(접근주체)
	 * 
	 * //세션만들기 if(principal != null) { session.setAttribute("principal", principal);
	 * } return new ResponseDto<Integer>(HttpStatus.OK.value(),1); }
	 */
}
