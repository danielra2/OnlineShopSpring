package mycode.onlineshopspring.orderDetails;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mycode.onlineshopspring.orders.Orders;
import mycode.onlineshopspring.products.Products;

@Entity
@Table(name="masina")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false,length = 255)
    private String sku;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    private Products product;


    @Override
    public String toString(){
        return "\n  ID: " + id +
                "\n  price " + price +
                "\n  sku: " + sku +
                "\n  quantity: " + quantity +
                "\n}";
    }




}
