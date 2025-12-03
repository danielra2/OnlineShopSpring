package mycode.onlineshopspring.customer.service;

import mycode.onlineshopspring.customer.dto.CustomerListResponse;
import mycode.onlineshopspring.customer.dto.CustomerResponse;
import mycode.onlineshopspring.customer.models.Customer;
import mycode.onlineshopspring.customer.repository.CustomerRepository;
import mycode.onlineshopspring.mappers.OnlineShopMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserQueryServiceImpl implements UserQuerryService {
    private final CustomerRepository customerRepository;
    private final OnlineShopMapper mapper;

    public UserQueryServiceImpl(CustomerRepository customerRepository,OnlineShopMapper mapper) {
        this.customerRepository=customerRepository;
        this.mapper = mapper;
    }

    @Override
    public CustomerListResponse findAllCustomers(int page, int size) {
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(size, 1), Sort.by("id"));
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        return new CustomerListResponse(
                mapper.mapCustomerListToResponseList(customerPage.getContent()),
                customerPage.getTotalElements(),
                customerPage.getTotalPages(),
                customerPage.getNumber(),
                customerPage.getSize());
    }
}
