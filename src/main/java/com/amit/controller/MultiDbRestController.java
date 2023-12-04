package com.amit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amit.mysql.entity.UserEmp;
import com.amit.mysql.repo.UserRepo;
import com.amit.oracle.entity.Product;
import com.amit.oracle.repo.ProductRepo;

@RestController
public class MultiDbRestController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ProductRepo productRepo;

	@GetMapping("/save")
	public String saveData() {

		UserEmp userEmp = new UserEmp();
		userEmp.setId(2);
		userEmp.setEmail("saffu@gmail.com");
		userEmp.setName("saffu");

		Product product = new Product();
		product.setId(2);
		product.setPrice(600.00);
		product.setName("Apple");

		userRepo.save(userEmp);
		productRepo.save(product);

		return "save successfully";
	}

	@GetMapping("/oracle")
	public List<Product> getOracleData() {
		
		return productRepo.findAll();	
	}
	
	@GetMapping("/mysql")
	public List<UserEmp> getMysqlData() {
		
		return userRepo.findAll();
	}
}
