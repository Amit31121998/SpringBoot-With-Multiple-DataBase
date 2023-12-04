package com.amit.mysql.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amit.mysql.entity.UserEmp;

public interface UserRepo extends JpaRepository<UserEmp, Integer> {

}
