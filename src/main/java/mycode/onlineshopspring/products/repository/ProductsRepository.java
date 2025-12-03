package mycode.onlineshopspring.products.repository;

import mycode.onlineshopspring.products.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products,Long> {

}
