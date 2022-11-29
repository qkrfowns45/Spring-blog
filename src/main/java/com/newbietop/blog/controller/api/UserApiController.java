package com.newbietop.blog.controller.api;

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
	
	@PostMapping("/api/user")
	public ResponseDto<Integer> save(@RequestBody User user) { // username,password,email외에 나머지는 알아서 들어간다.
		System.out.println("UserApiController : save호출됨");
		//실제로 DB에 insert를 하고 아래에서 return한다.
		user.setRole(RoleType.USER);
		int result = userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),result);
	}
}
