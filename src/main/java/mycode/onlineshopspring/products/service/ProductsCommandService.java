package mycode.onlineshopspring.products.service;

import jakarta.transaction.Transactional;
import mycode.onlineshopspring.products.dto.ProductsDto;
import mycode.onlineshopspring.products.dto.ProductsResponse;

public interface ProductsCommandService {
    ProductsResponse createProduct(ProductsDto dto);
}
