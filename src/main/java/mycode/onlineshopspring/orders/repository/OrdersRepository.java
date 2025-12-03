package mycode.onlineshopspring.orders.repository;

import mycode.onlineshopspring.orders.models.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders,Long> {

    @Override
    @EntityGraph(attributePaths = {"orderDetailsSet", "orderDetailsSet.product"})
    List<Orders> findAll();

    @EntityGraph(attributePaths = {"orderDetailsSet", "orderDetailsSet.product"})
    Page<Orders> findAll(Pageable pageable);

}
