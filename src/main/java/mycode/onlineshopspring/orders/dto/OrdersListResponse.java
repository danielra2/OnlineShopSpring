package mycode.onlineshopspring.orders.dto;
import java.util.List;
public record OrdersListResponse(List<OrdersResponse>ordersList){}