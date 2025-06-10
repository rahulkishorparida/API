package com.cms.demo.controller;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cms.demo.config.ApiResponse;
import com.cms.demo.dto.CustomerDto;
import com.cms.demo.model.Customer;
import com.cms.demo.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;
    @Autowired
    private ObjectMapper mapper;
//class convert java object to jshon
    
    @PostMapping("/customer")
    public ResponseEntity<ApiResponse> saved(@RequestBody CustomerDto customerDto) {
    
        try {
//            ObjectMapper mapper = new ObjectMapper(); // You can also inject it with @Autowired
//            String json = mapper.writeValueAsString(customerDto);
        	
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(customerDto);
            logger.info("Request to save customer: {}", json);

            boolean saved = customerService.saveCustomer(customerDto);

            if (saved) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ApiResponse(true, "Customer created successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse(false, "Failed to create customer"));
            }

        } catch (Exception e) {
            logger.error("Exception while saving customer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Something went wrong"));
        }
    }

//        logger.info("Request to save customer: {}", customerDto);
//        boolean saveCustomer = customerService.saveCustomer(customerDto);
//        if (saveCustomer) {
//            logger.info("Customer created successfully");
//            return new ResponseEntity<>("New customer created", HttpStatus.CREATED);
//        } else {
//            logger.error("Failed to create customer");
//            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @DeleteMapping("/customer/{id}")
//    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
//        logger.info("Request to delete customer with id: {}", id);
//        boolean deleted = customerService.deletebyId(id);
//        if (deleted) {
//            logger.info("Customer with id {} deleted", id);
//            return new ResponseEntity<>("deleted", HttpStatus.OK);
//        } else {
//            logger.warn("Customer with id {} not found", id);
//            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
//        }
//    }
    
    @DeleteMapping("/customer/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Integer id) {
        logger.info("Request to delete customer with id: {}", id);
        boolean deleted = customerService.deletebyId(id);
        if (deleted) {
            logger.info("Customer with id {} deleted", id);
            return ResponseEntity.ok(new ApiResponse(true, "Customer deleted successfully"));
        } else {
            logger.warn("Customer with id {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Customer not found"));
        }
    }


//    @GetMapping("/customer/{id}")
//    public ResponseEntity<?> findbyId(@PathVariable Integer id) {
//        logger.info("Request to find customer by id: {}", id);
//        Optional<Customer> find = customerService.findbyId(id);
//        if (find.isEmpty()) {
//            logger.warn("Customer with id {} not found", id);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
//        }
//        logger.info("Customer found: {}", find.get());
//        return new ResponseEntity<>(find.get(), HttpStatus.OK);
//    }
    
    @GetMapping("/customer/{id}")
    public ResponseEntity<ApiResponse<Customer>> findbyId(@PathVariable Integer id) {
        logger.info("Request to find customer by id: {}", id);
        Optional<Customer> customerOpt = customerService.findbyId(id);

        if (customerOpt.isEmpty()) {
            logger.warn("Customer with id {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Customer not found"));
        }

        Customer customer = customerOpt.get();
        logger.info("Customer found: {}", customer);
        return ResponseEntity.ok(new ApiResponse<>(true, "Customer found", customer));
//        return ResponseEntity.status(new ApiResponse(true, "Customer found", customer));
    }

//
//    @GetMapping("/customers")
//    public ResponseEntity<?> findAll() {
//        logger.info("Request to get all customers");
//        List<Customer> c = customerService.getdAllCustomer();
//        logger.info("Number of customers found: {}", c.size());
//        return new ResponseEntity<>(c, HttpStatus.OK);
//    }
    
//    @GetMapping("/customers")
//    public ResponseEntity<ApiResponse<List<Customer>>> findAll() {
//        logger.info("Request to get all customers");
//        List<Customer> customers = customerService.getdAllCustomer();
//        logger.info("Number of customers found: {}", customers.size());
//        return ResponseEntity.ok(new ApiResponse<>(true, "Customers fetched successfully", customers));
//    }
    @GetMapping("/customers")
    public ResponseEntity<ApiResponse<List<Customer>>> findAll() {
        try {
            List<Customer> allCustomers = customerService.getdAllCustomer();

            // Optional: Log the JSON of the list
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(allCustomers);
            logger.info("Fetched customers: {}", json);

            if (allCustomers.isEmpty()) {
                logger.warn("No customers found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "No customers found"));
            }

//            return ResponseEntity.ok(new ApiResponse<>(true, "Customers found", allCustomers));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(true, "Customers found", allCustomers));

        } catch (Exception e) {
            logger.error("Error while fetching customers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error fetching customers"));
        }
    }




//    @PutMapping("/customer/{id}")
//    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody CustomerDto customerDto) {
//        logger.info("Request to update customer with id: {} and data: {}", id, customerDto);
//        boolean updateCustomer = customerService.updateCustomer(id, customerDto);
//        if (updateCustomer) {
//            logger.info("Customer with id {} updated successfully", id);
//            return new ResponseEntity<>("updated", HttpStatus.OK);
//        } else {
//            logger.warn("Failed to update customer with id {}", id);
//            return new ResponseEntity<>("not", HttpStatus.NOT_MODIFIED);
//        }
//    }
    
    @PutMapping("/customer/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Integer id, @RequestBody CustomerDto customerDto) {
        logger.info("Request to update customer with id: {} and data: {}", id, customerDto);
        boolean updated = customerService.updateCustomer(id, customerDto);

        if (updated) {
            logger.info("Customer with id {} updated successfully", id);
            return ResponseEntity.ok(new ApiResponse(true, "Customer updated successfully"));
        } else {
            logger.warn("Failed to update customer with id {}", id);
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                    .body(new ApiResponse(false, "Customer not modified or not found"));
        }
    }

}
