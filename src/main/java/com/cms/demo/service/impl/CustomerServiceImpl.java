package com.cms.demo.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.cms.demo.dto.CustomerDto;
import com.cms.demo.model.Customer;
import com.cms.demo.repository.CustomerRepository;
import com.cms.demo.service.CustomerService;

import jakarta.transaction.Transactional;
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
	private CustomerRepository customerRepository;

    @Override
    public boolean saveCustomer(CustomerDto customerDto) {
    	
    if(customerRepository.existsByphone(customerDto.getPhone())) {
    	throw new RuntimeException("Phone number already exists");
    }	
        try {
            Customer customer = new Customer();
            customer.setName(customerDto.getName());
            customer.setAge(customerDto.getAge());
            customer.setPhone(customerDto.getPhone());


            String base64Image = customerDto.getImagebase64();

            if (base64Image != null && !base64Image.isEmpty()) {
                String extension = "png";
                String imageData;
                //It will be decoded(Image)later into a byte array.

                if (base64Image.contains(",")) {
                    String[] parts = base64Image.split(",");
                    String metaInfo = parts[0];
                    imageData = parts[1];

                    // Extract the file extension from metadata (e.g. data:image/jpeg;base64)
                    if (metaInfo.contains("image/")) {
                        extension = metaInfo.substring(metaInfo.indexOf("/") + 1, metaInfo.indexOf(";"));
                    }
                } else {
                    imageData = base64Image;
                }
                
                byte[] imageBytes = Base64.getDecoder().decode(imageData);
                String imageName = "customer_" + System.currentTimeMillis() + "." + extension;
                String uploadDir = "uploads/";

                File uploadFolder = new File(uploadDir);
                if (!uploadFolder.exists()) {
                    uploadFolder.mkdirs();
                }

                String filePath = uploadDir + imageName;

                try (OutputStream os = new FileOutputStream(filePath)) {
                    os.write(imageBytes);
                }

                customer.setImage(filePath);
            }

            Customer saved = customerRepository.save(customer);
//            return saved != null;
//            return true;
            convertToDto(saved);
            return saved != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


	@Override
	public boolean deletebyId(Integer id) {
	if(customerRepository.existsById(id)) {
		customerRepository.deleteById(id);
		return true;
	}
		return false;
	}

	@Override
	public Optional<Customer> findbyId(Integer id) {
		Optional<Customer> c = customerRepository.findById(id);
		return c;		
	}

	@Override
	public List<Customer> getdAllCustomer() {
		List<Customer> c =customerRepository.findAll();
		return c;
	}
	
	@Override
	public boolean updateCustomer(Integer id, CustomerDto customerDto) {
	    Optional<Customer> c = customerRepository.findById(id);

	    if (c.isPresent()) {
	        Customer customer = c.get();
	        customer.setName(customerDto.getName());
	        customer.setAge(customerDto.getAge());
	        customer.setPhone(customerDto.getPhone());

	        String base64Image = customerDto.getImagebase64();

	        if (base64Image != null && !base64Image.trim().isEmpty()) {
	            String extension = "png";
	            String imageData = base64Image;

	            if (base64Image.contains(",")) {
	                String[] parts = base64Image.split(",");
	                String metaInfo = parts[0];
	                imageData = parts[1];

	                if (metaInfo.contains("image/")) {
	                    extension = metaInfo.substring(metaInfo.indexOf("/") + 1, metaInfo.indexOf(";"));
	                }
	            }
	            
	            try {
	                byte[] imageBytes = Base64.getDecoder().decode(imageData);
	                String imageName = "customer_" + System.currentTimeMillis() + "." + extension;
	                String uploadDir = "uploads/";
	                File uploadFolder = new File(uploadDir);

	                if (!uploadFolder.exists() && !uploadFolder.mkdirs()) {
	                    throw new IOException("Could not create upload directory.");
	                }

	                String filePath = uploadDir + imageName;
	                
	                try (OutputStream os = new FileOutputStream(filePath)) {
	                    os.write(imageBytes);
	                }

	                String oldImagePath = customer.getImage();
	                if (oldImagePath != null) {
	                	
	                    File oldImageFile = new File(oldImagePath);
	                    if (oldImageFile.exists()) {
	                        oldImageFile.delete(); 
	                    }
	                }

	                customer.setImage(filePath);

	            } catch (IOException e) {
	                e.printStackTrace();
	                return false;
	            }
	        }

	        customerRepository.save(customer);
	        return true;
	    }

	    return false;
	}

	
	public CustomerDto convertToDto(Customer customer) {
	    CustomerDto customerDto = new CustomerDto();
	    customerDto.setId(customer.getId());
	    customerDto.setName(customer.getName());
	    customerDto.setAge(customer.getAge());
	    customerDto.setPhone(customer.getPhone());
	    customerDto.setImagebase64(customer.getImage());
		return customerDto;
}



    }

//String imagePath = customer.getImage();
//if (imagePath != null) {
//    try {
//        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
//        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
//        customerDto.setImagebase64("data:image/png;base64," + base64Image); // You can detect type dynamically if needed
//    } catch (IOException e) {
//        e.printStackTrace();
//        customerDto.setImagebase64(null);
//    }
//}


