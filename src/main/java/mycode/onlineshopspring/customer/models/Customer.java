package mycode.onlineshopspring.customer.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mycode.onlineshopspring.orders.Orders;
import org.hibernate.query.Order;

import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name="customer")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 255)
    private String email;

    @Column(nullable = false,length=255)
    private String password;

    @Column(nullable = false,length = 255)
    private String fullName;

    @Column(nullable = false,length = 255)
    private String billingAddress;

    @Column(nullable = false,length = 255)
    private String defaultShippingAddress;

    @Column(nullable = false,length = 255)
    private String country;

    @Column(nullable = false,length = 255)
    private String phone;

    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Orders> orderSet=new TreeSet<>();
}
