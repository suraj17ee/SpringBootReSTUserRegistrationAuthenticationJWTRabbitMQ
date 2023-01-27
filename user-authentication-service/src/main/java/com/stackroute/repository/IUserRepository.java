package com.stackroute.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stackroute.entity.User;

public interface IUserRepository extends JpaRepository<User, String> {

}
