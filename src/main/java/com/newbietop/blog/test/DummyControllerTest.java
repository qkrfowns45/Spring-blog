package com.newbietop.blog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newbietop.blog.model.RoleType;
import com.newbietop.blog.model.User;
import com.newbietop.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {

	@Autowired //DummyControllerTest를 생성할때 자동으로 의존성을 주입해준다.(메모리에 띄워줌)
	private UserRepository userRepository;
	
	@PostMapping("/dummy/join")
	private String join(User user){
		
		System.out.println("id : " + user.getId());
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());		
		System.out.println("role : " + user.getRole());		
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		
		return "회원가입이 완료되었습니다.test";
	}
}
