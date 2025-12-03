package mycode.onlineshopspring.products.service;
import mycode.onlineshopspring.products.dto.ProductsListResponse;
public interface ProductsQuerryService{
    ProductsListResponse findAllProducts();
}