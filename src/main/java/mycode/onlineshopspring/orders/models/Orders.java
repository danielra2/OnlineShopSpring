package mycode.onlineshopspring.orders.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import mycode.onlineshopspring.common.model.AuditableEntity;
import mycode.onlineshopspring.customer.models.Customer;
import mycode.onlineshopspring.orderDetails.models.OrderDetails;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"customer", "orderDetailsSet"})
public class Orders extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int ammount;

    @Column(nullable = false,length = 255)
    private String shippingAdress;

    @Column(nullable = false,length = 255)
    private String orderAdress;

    @Column(nullable = false,length = 255)
    private String orderEmail;

    @Column(nullable = false,length = 255)
    private LocalDate order_date;

    @Column(nullable = false)
    private String orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private Customer customer;

    @OneToMany(
            mappedBy = "order",
            cascade=CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<OrderDetails> orderDetailsSet = new HashSet<>();

    public void addOrderDetails(OrderDetails detail){
        orderDetailsSet.add(detail);
        detail.setOrder(this);
    }
}
