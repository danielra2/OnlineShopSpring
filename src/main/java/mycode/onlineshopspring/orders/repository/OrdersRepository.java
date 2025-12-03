package mycode.onlineshopspring.orders.repository;

import mycode.onlineshopspring.orders.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders,Long> {

}
