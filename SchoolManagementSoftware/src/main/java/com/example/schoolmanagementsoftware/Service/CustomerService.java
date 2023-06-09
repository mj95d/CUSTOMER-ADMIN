package com.example.schoolmanagementsoftware.Service;

import com.example.schoolmanagementsoftware.Model.Customer;
import com.example.schoolmanagementsoftware.Model.Product;
import com.example.schoolmanagementsoftware.Repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {



    private final CustomerRepository customerRepository;
        private final ProductService productService;
    private String name;

    public CustomerService(CustomerRepository orderItemRepository, ProductService productService) {
        this.customerRepository = orderItemRepository;
        this.productService = productService;
    }

    public Customer customer(double productId) {
        Product product = productService.getProductByName(name);
            Customer customer = new Customer();
            customer.setId(product.getId());
            customer.setPassword(String.valueOf(false));
            customer.setRole(String.valueOf(product.getPrice() * productId));
            return customerRepository.save(customer);
        }

        public Customer getCustomer(Integer id, @Valid Customer customer) {
            return (Customer)  customerRepository.findById(id).orElse(null);
        }

    public void addCustomer(Customer customer) {
    }

    public void updateCustomer(Integer integer, Customer customer) {
    }

    public void deleteCustomer(Integer integer) {
    }

    public void getAllCustomer(Integer integer) {
    }
}

