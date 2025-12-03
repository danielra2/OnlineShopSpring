package mycode.onlineshopspring.orderDetails.repository;

import mycode.onlineshopspring.orderDetails.OrderDetails;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Long> {
    List<OrderDetails> findAll();
}
