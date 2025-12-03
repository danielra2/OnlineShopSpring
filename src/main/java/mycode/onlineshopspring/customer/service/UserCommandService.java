package mycode.onlineshopspring.customer.service;

import jakarta.transaction.Transactional;
import mycode.onlineshopspring.customer.dto.CustomerDto;
import mycode.onlineshopspring.customer.dto.CustomerResponse;
import mycode.onlineshopspring.exceptions.ProductDoesntExistException;

public interface UserCommandService {
    @Transactional
    CustomerResponse createCustomer(CustomerDto dto) throws ProductDoesntExistException;
}
