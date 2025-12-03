package mycode.onlineshopspring.customer.service;

import mycode.onlineshopspring.customer.dto.CustomerResponse;
import java.util.List;

public interface UserQuerryService {
    List<CustomerResponse> findAllCustomers();
}