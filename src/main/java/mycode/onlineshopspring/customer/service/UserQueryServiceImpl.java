package mycode.onlineshopspring.customer.service;

import mycode.onlineshopspring.common.pagination.PaginationUtils;
import mycode.onlineshopspring.customer.dto.CustomerListResponse;
import mycode.onlineshopspring.customer.dto.CustomerResponse;
import mycode.onlineshopspring.customer.models.Customer;
import mycode.onlineshopspring.customer.repository.CustomerRepository;
import mycode.onlineshopspring.mappers.OnlineShopMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static mycode.onlineshopspring.common.pagination.PaginationUtils.fetchPage;

@Service
public class UserQueryServiceImpl implements UserQuerryService {
    private final CustomerRepository customerRepository;
    private final OnlineShopMapper mapper;

    public UserQueryServiceImpl(CustomerRepository customerRepository,OnlineShopMapper mapper) {
        this.customerRepository=customerRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerListResponse findAllCustomers(int page, int size) {
        Pageable pageable = PaginationUtils.sanitize(page, size, Sort.by("id"));
        Page<Customer> customerPage = fetchPage(customerRepository::findAll, pageable);
        List<CustomerResponse> customers = mapper.mapCustomerListToResponseList(customerPage.getContent());

        return new CustomerListResponse(
                customers,
                customerPage.getTotalElements(),
                customerPage.getTotalPages(),
                customerPage.getNumber(),
                customerPage.getSize());
    }
}
