package mycode.onlineshopspring.customer.service;

import jakarta.transaction.Transactional;
import mycode.onlineshopspring.customer.dto.CustomerDto;
import mycode.onlineshopspring.customer.dto.CustomerResponse;

public interface UserCommandService {
    @Transactional
    CustomerResponse createCustomer(CustomerDto dto);
}
