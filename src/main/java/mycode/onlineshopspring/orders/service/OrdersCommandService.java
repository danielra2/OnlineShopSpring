package mycode.onlineshopspring.orders.service;

import jakarta.transaction.Transactional;
import mycode.onlineshopspring.exceptions.CustomerDoesntExistException;
import mycode.onlineshopspring.orders.dto.OrdersDto;
import mycode.onlineshopspring.orders.dto.OrdersResponse;

public interface OrdersCommandService {
    @Transactional
    OrdersResponse createOrder(Long customerId, OrdersDto dto)throws CustomerDoesntExistException;
}
