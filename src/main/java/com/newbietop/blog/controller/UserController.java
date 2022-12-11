package com.newbietop.blog.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newbietop.blog.config.auth.PrincipalDetail;
import com.newbietop.blog.model.KakaoProfile;
import com.newbietop.blog.model.OAuthToken;
import com.newbietop.blog.model.User;
import com.newbietop.blog.service.UserService;

//인증이 안된 사용자들이 출입할 수 있는 경로를 /auth로만 허용
//그냥 주소가 /이면 index.jsp허용
//static이하에 있는 /js/~~ ,/css/~~ 등은 허용

@Controller
public class UserController {
	
	@Autowired
	private AuthenticationManager authenticationManager; 
	
	//세션처리할때 Oauth가 노출될 수 있으니 절대로 노출되지 말자
	@Value("${newbietop.key}")
	private String newbietopKey;
	
	@Autowired
	private UserService userService;

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		
		return "user/loginForm";
	}
	
	//Data를 리턴해주는 컨트롤러 함수
	@GetMapping("/auth/kakao/callback")
	public @ResponseBody ResponseEntity<?> kakaoCallback(String code) {
		
		//POST방식으로 key=value 데이터를 요청(카카오쪽으로)
		//Retrofit2 안드로이드에서 많이씀
		//OkHttp
		//RestTemplate
		
		RestTemplate rt = new RestTemplate();
		
		//HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "c8d6aa91c2196bc8b4ede1c1b6e41d90");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		//HttpHeader와 HttpBody를 하나의 오브젝토에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params,headers);
		
		//Http 요청하기 - Post방식으로 - 그리고 response변수에 응답받음
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class
		);
		
		//Gson, Json Simple, ObjectMapper
		ObjectMapper obMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = obMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		//위 내용 복사해서 사용자 정보 조회 요청
		RestTemplate rt2 = new RestTemplate();
		
		//HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		
		//HttpHeader와 HttpBody를 하나의 오브젝토에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenProfileRequest = new HttpEntity<>(headers2);
		
		//Http 요청하기 - Post방식으로 - 그리고 reponse변수에 응답받음
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoTokenProfileRequest,
				String.class
		);
		
		ObjectMapper obMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = obMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		User kakaouser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
				.password(newbietopKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		
		//가입자인지 혹은 비가입자 체크 해서 처리
		User originuser = userService.회원찾기(kakaouser.getUsername());
		
		if(originuser.getUsername() == null) {
			System.err.println("기존 회원이 아니기에 회원가입을 진행합니다.");
			userService.회원가입(kakaouser);
		}
		
		System.out.println("자동로그인을 진행합니다.");
		
		//로그인 처리
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaouser.getUsername(), newbietopKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println(authentication.getPrincipal()+" "+ authentication.getName());
		System.out.println(SecurityContextHolder.getContext().getAuthentication());
		
		HttpHeaders headers3 = new HttpHeaders();
        headers3.setLocation(URI.create("/"));
        return new ResponseEntity<>(headers3, HttpStatus.MOVED_PERMANENTLY);
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		
		return "user/updateForm";
	}
}
