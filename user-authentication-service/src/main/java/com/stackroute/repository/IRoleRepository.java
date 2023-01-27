package com.stackroute.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stackroute.entity.Role;

public interface IRoleRepository extends JpaRepository<Role, String> {

}
