package com.newbietop.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newbietop.blog.model.User;

//@Repository 생략이 가능하다 자동으로 bean 등록이 됨
public interface UserRepository extends JpaRepository<User, Integer>{

}
