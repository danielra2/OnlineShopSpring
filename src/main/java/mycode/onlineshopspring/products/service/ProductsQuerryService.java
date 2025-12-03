package mycode.onlineshopspring.products.service;
import mycode.onlineshopspring.products.dto.ProductsListResponse;
import mycode.onlineshopspring.products.dto.ProductsResponse;
import mycode.onlineshopspring.products.models.Products;

import java.util.Optional;

public interface ProductsQuerryService{
    ProductsListResponse findAllProducts();
    Optional<ProductsResponse>findProductById(Long id);
}