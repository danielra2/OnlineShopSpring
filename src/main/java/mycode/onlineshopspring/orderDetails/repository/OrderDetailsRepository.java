package mycode.onlineshopspring.orderDetails.repository;

import mycode.onlineshopspring.orderDetails.models.OrderDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Long> {
    @Override
    @EntityGraph(attributePaths = "product")
    List<OrderDetails> findAll();

    @EntityGraph(attributePaths = "product")
    Page<OrderDetails> findAll(Pageable pageable);
}
