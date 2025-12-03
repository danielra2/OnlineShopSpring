package mycode.onlineshopspring.orders.dto;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

public record OrdersDto(
        int ammount,
        String shippingAdress,
        String orderAddress,
        String orderEmail,
        LocalDate order_date,
        String orderStatus


) {

}
