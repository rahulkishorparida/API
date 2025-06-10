package com.cms.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.demo.dto.CustomerDto;
import com.cms.demo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	boolean existsByphone(long phone);

}
