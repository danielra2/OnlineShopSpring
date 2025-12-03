package mycode.onlineshopspring.customer.service;

import mycode.onlineshopspring.customer.dto.CustomerResponse;
import mycode.onlineshopspring.customer.mappers.CustomerMapper; // Trebuie să fie OnlineShopMapper
import mycode.onlineshopspring.customer.models.Customer;
import mycode.onlineshopspring.customer.repository.CustomerRepository;
import mycode.onlineshopspring.mappers.OnlineShopMapper; // Asumând că acesta este noul mapper
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserQueryServiceImpl implements UserQuerryService {
    private final CustomerRepository customerRepository;
    private final OnlineShopMapper mapper;

    public UserQueryServiceImpl(CustomerRepository customerRepository,OnlineShopMapper mapper) {
        this.customerRepository=customerRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CustomerResponse> findAllCustomers() {
        List<Customer> customerList=customerRepository.findAll();
        return mapper.mapCustomerListToResponseList(customerList);
    }
}