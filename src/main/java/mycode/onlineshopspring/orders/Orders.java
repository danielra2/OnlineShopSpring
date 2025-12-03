package mycode.onlineshopspring.orders;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mycode.onlineshopspring.customer.models.Customer;
import mycode.onlineshopspring.orderDetails.OrderDetails;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name="orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Orders {
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
    private Set<OrderDetails> orderDetailsSet=new TreeSet<>();





}
