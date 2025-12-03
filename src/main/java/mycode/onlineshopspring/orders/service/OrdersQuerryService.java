package mycode.onlineshopspring.orders.service;
import mycode.onlineshopspring.orders.dto.OrdersListResponse;
public interface OrdersQuerryService{
    OrdersListResponse findAllOrders();
}