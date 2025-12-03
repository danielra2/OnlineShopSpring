package mycode.onlineshopspring.products.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mycode.onlineshopspring.common.model.AuditableEntity;
import mycode.onlineshopspring.orderDetails.models.OrderDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name="products")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class Products extends AuditableEntity {
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
    private Set<OrderDetails> orderDetailsSet = new TreeSet<>();

}
