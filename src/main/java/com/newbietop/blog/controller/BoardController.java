package com.newbietop.blog.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.newbietop.blog.config.auth.PrincipalDetail;
import com.newbietop.blog.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	//컨트롤러에서 세션을 어떻게 찾는지?
	@GetMapping({"","/"})
	public String index(Model model, @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
		
//		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("newbietop", "1234"));
//		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		model.addAttribute("boards",boardService.글목록(pageable));
		return  "index"; //veiwResolver 작동!! 해당 index페이지로 모델의 정보를 들고 이동!
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board",boardService.상세보기(id));
		return "board/detail";
		
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id,Model model) {
		model.addAttribute("board",boardService.상세보기(id));
		return "board/updateForm";
	}
	
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
}
