package mycode.onlineshopspring.customer.repository;

import mycode.onlineshopspring.customer.models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Override
    @EntityGraph(attributePaths = {"orderSet", "orderSet.orderDetailsSet", "orderSet.orderDetailsSet.product"})
    List<Customer> findAll();

    @EntityGraph(attributePaths = {"orderSet", "orderSet.orderDetailsSet", "orderSet.orderDetailsSet.product"})
    Page<Customer> findAll(Pageable pageable);
}
