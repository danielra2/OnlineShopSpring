package mycode.onlineshopspring.orderDetails.service;
import mycode.onlineshopspring.orderDetails.dto.OrderDetailsListResponse;
public interface OrderDetailsQuerryService{
    OrderDetailsListResponse findAllOrderDetails();
}