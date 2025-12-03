package mycode.onlineshopspring.orders.dto;
import mycode.onlineshopspring.orderDetails.dto.OrderDetailsResponse;
import java.time.LocalDate;
import java.util.Set;
public record OrdersResponse(Long id, int ammount, String shippingAdress, String orderAddress, String orderEmail, LocalDate order_date, String orderStatus, Set<OrderDetailsResponse> orderDetailsSet) {}