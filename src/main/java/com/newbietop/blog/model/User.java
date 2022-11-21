package com.newbietop.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity//User 클래스가 MySQL에 테이블이 생성된다.
public class User {

	@Id //Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; //시퀀스,auto_increment
	
	@Column(nullable = false, length = 30)
	private String username; //아이디

	@Column(nullable = false, length = 100) //암호화할거라 넉넉하게 사이즈 조정
	private String password; //패스워드
	
	@Column(nullable = false, length = 50)
	private String email; //이메일
	
	@ColumnDefault("'user'")
	private String role; //Enum을 쓰는게 좋다 .//회원의 권한 설정임
	
	@CreationTimestamp //시간이 자동으로 입력
	private Timestamp createDate;
}
