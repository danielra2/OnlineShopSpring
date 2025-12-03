package mycode.onlineshopspring.utils;

import mycode.onlineshopspring.products.dto.ProductsResponse;
import mycode.onlineshopspring.products.models.Products;
import mycode.onlineshopspring.utils.dto.CartItemDto;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class Cart {
    private final Set<CartItemDto> cart=new HashSet<>();

    public void addItem(ProductsResponse product,int quantity){
        Optional<CartItemDto>existingItem=cart.stream().filter(i->i.productId().equals(product.id())).findFirst();//gaseste produs dupa id
        if(existingItem.isPresent()){ // daca exista ii mareste doar cantitatea, nu l scrie de 2 ori
            CartItemDto oldItem=existingItem.get();
            cart.remove(oldItem);
            if (existingItem.isPresent()) {
                cart.add(new CartItemDto(product.id(), oldItem.quantity()+quantity, product.sku(), product.price(), product.name()));
            }
            else{
                cart.add(new CartItemDto(product.id(),quantity, product.sku(), product.price(), product.name()));
            }
        }
    }
    public boolean removeItem(Long productId){
        return cart.removeIf(cart->cart.productId().equals(productId));
    }
    public Set<CartItemDto>getCart(){
        return cart;
    }
    public void clear(){
        cart.clear();
    }
}
