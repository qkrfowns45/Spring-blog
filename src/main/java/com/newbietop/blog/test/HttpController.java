package com.newbietop.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청->응답(HTML)
//@Controller

//시용자가 요청 -> 응답(Data)
@RestController
public class HttpController {
	
	private static final String TAG = "HttpController : ";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		Member m = new Member(1, "newbietop", "qk45qk45", "email");
		System.out.println(TAG + "gatter : "+m.getId());
		m.setId(5000);
		System.out.println(TAG + "setter : "+m.getId());
		
		return "lombok test 완료";
	}
	
	@GetMapping("/http/get")
	public String getTest(Member m) {
		return "get 요청:" + m.getId()+ "," + m.getUsername()+ "," + m.getPassword() + "," +m.getEmail();
	}
	
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) {
		return "post 요청:" + + m.getId()+ "," + m.getUsername()+ "," + m.getPassword() + "," +m.getEmail();
	}
	
	@PutMapping("/http/put")
	public String putTest() {
		return "put 요청";
	}
	
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
}
