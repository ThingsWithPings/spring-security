package com.thingswithpings.controller;

import com.thingswithpings.model.Customer;
import com.thingswithpings.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody Customer customer){
        try {
            String pwdHash = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(pwdHash);
            Customer savedCustomer = customerRepository.save(customer);

            if (savedCustomer.getId() > 0){
                return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
            }
            else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User creation failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception occurred" + e.getMessage());
        }
    }
}
