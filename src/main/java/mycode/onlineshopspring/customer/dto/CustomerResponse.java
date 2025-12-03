package mycode.onlineshopspring.customer.dto;
import mycode.onlineshopspring.orders.dto.OrdersResponse;
import java.util.Set;
public record CustomerResponse(Long id, String email, String password, String billingAddress, String fullName, String defaultShippingAddress, String country, String phone, Set<OrdersResponse> orderSet) {}