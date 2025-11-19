package mycode.onlineshopspring.products;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mycode.onlineshopspring.orderDetails.OrderDetails;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name="products")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 255)
    private String sku;

    @Column(nullable = false,length = 255)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int weight;

    @Column(nullable = false,length = 255)
    private String descriptions;

    @Column(nullable = false,length = 255)
    private String category;

    @Column(nullable = false,length = 255)
    private Date createDate;

    @Column(nullable = false)
    private int stock;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<OrderDetails> orderDetailsSet=new TreeSet<>();




}
