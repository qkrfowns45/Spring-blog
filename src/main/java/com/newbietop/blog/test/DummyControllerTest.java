package com.newbietop.blog.test;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	//{id}주소로 파라미터를 전달 받을 수 있다.
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		//id를 없는 값으로 조회하면 null이 리턴된다
		//Optional로 User객체를 감싸서 null인지 아닌지 판단해서 리턴
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다.id : " + id);
			}
		});
		
		//요청 : 웹 브라우저 리턴을 html이 아니라 data를 리턴해주는 restcontroller이다. 웹 브라우저는 자바 오브젝트를 이해못한다.
		//변환을 해주어야 한다.(웹 브라우저가 이해할 수 있는 데이터)->json이다.
		//스프링부트 = MessageConverter라는 애가 자동으로 응답한다.
		//만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson이라는 라이브러리를 호출해서 json으로 던져준다.
		//user 객체 = 자바 오브젝트
		return user;
	}
	
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
