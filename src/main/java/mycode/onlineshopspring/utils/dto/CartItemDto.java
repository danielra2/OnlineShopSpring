package mycode.onlineshopspring.utils.dto;

public record CartItemDto(
        Long productId,int quantity,String sku,int price,String name
) {
}
