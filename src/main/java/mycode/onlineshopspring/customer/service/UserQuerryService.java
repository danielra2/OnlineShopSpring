package mycode.onlineshopspring.customer.service;

import mycode.onlineshopspring.customer.dto.CustomerListResponse;

public interface UserQuerryService {
    CustomerListResponse findAllCustomers(int page, int size);
}
