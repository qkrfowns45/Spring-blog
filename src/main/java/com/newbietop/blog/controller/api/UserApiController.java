package com.newbietop.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.newbietop.blog.config.auth.PrincipalDetail;
import com.newbietop.blog.dto.ResponseDto;
import com.newbietop.blog.model.RoleType;
import com.newbietop.blog.model.User;
import com.newbietop.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager; 

	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) { // username,password,email외에 나머지는 알아서 들어간다.
		System.out.println("UserApiController : save호출됨");
		//실제로 DB에 insert를 하고 아래에서 return한다.
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user){
		userService.회원수정(user);
		//여기서 db의 데이터는 변경이 됐지만 세션값은 변경안됨
		//직접 세션값을 변경해줄것이다.
		//강제로 세션값을 바꾸는 것
		
		//세션등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

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
