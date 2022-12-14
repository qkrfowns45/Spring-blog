package com.newbietop.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob //대용량 데이터 일때 사용
	private String content; //섬머노트라이브러리 사용 예정
	
	private int count; //조회수
	
	@ManyToOne(fetch = FetchType.EAGER)// Many = board, User = One
	@JoinColumn(name = "userId")
	private User user; //DB는 오브젝트를 저장할 수 없다. FK 자바는 오브젝트를 저장할 수 있다.
	
	@OneToMany(mappedBy = "board",fetch = FetchType.EAGER) //mappedby 연관관계의 주인이 아니다.(난 FK가 아니에요) DB에 컬럼을 만들지 않는다 조인을해서 값을 가져오겠다!
	@JsonIgnoreProperties({"board"}) //이렇게하면 reply를 호출할때 reply안에 있는 board는 무시한다!!
	private List<Reply> replys;
	
	@CreationTimestamp
	private Timestamp createDate;
}
