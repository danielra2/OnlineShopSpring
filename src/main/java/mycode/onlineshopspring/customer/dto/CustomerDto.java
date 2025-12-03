package mycode.onlineshopspring.customer.dto;

import mycode.onlineshopspring.orders.dto.OrdersDto;

import java.util.Set;

public record CustomerDto(
        String email,
        String password,
        String billingAddress,
        String fullName,
        String defaultShippingAddress,
        String country,
        String phone,
        Set<OrdersDto> orderSet

) {

}
