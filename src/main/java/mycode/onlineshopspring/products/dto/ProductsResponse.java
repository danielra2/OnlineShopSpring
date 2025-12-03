package mycode.onlineshopspring.products.dto;
import java.util.Date;
public record ProductsResponse(Long id, String sku, String name, int price, int weight, String descriptions, String category, Date createDate, int stock) {

}