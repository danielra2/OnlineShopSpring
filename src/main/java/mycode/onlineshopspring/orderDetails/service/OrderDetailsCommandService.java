package mycode.onlineshopspring.orderDetails.service;

import jakarta.transaction.Transactional;
import mycode.onlineshopspring.exceptions.ProductDoesntExistException;
import mycode.onlineshopspring.orderDetails.dto.OrderDetailsDto;
import mycode.onlineshopspring.orderDetails.dto.OrderDetailsResponse;

public interface OrderDetailsCommandService {
    @Transactional
    OrderDetailsResponse createOrderDetails(OrderDetailsDto dto)throws ProductDoesntExistException;
}
