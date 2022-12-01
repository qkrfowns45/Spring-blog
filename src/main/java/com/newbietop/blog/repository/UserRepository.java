package com.newbietop.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.newbietop.blog.model.User;

//@Repository 생략이 가능하다 자동으로 bean 등록이 됨
public interface UserRepository extends JpaRepository<User, Integer>{
}


//JPA Naming 쿼리
	// SELECT * FROM user WHERE username = ? AND password = ?;
	//아래 둘중 하나를 선택하면 된다.
	//	User findByUsernameAndPassword(String username,String password);
	
	//	@Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2",nativeQuery = true)
	//	User login(String username,String password);
