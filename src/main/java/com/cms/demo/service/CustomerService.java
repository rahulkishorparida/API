package com.cms.demo.service;

import java.util.List;
import java.util.Optional;

import com.cms.demo.dto.CustomerDto;
import com.cms.demo.model.Customer;

public interface CustomerService {
	
	boolean saveCustomer(CustomerDto customerDto);
	boolean deletebyId(Integer id);
	Optional<Customer> findbyId(Integer id);
	List<Customer> getdAllCustomer();
	boolean updateCustomer(Integer id, CustomerDto customerDto);
//	CustomerDto existsphone(long phone);
	
}
