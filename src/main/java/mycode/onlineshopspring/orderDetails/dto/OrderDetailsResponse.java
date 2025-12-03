package mycode.onlineshopspring.orderDetails.dto;
import mycode.onlineshopspring.products.dto.ProductsResponse;
public record OrderDetailsResponse(Long id, int price, String sku, int quantity, ProductsResponse product) {

}