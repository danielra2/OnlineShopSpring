package mycode.onlineshopspring.customer.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import mycode.onlineshopspring.common.model.AuditableEntity;
import mycode.onlineshopspring.orders.models.Orders;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="customer")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, exclude = "orderSet")
public class Customer extends AuditableEntity {
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
    private Set<Orders> orderSet = new HashSet<>();

    @Override
    public String toString() {
        return "Customer Details {" +
                "\n  ID: " + id +
                "\n  Full Name: " + fullName +
                "\n  Email: " + email +
                "\n  Phone: " + phone +
                "\n  Country: " + country +
                "\n  Billing Address: " + billingAddress +
                "\n  Default Shipping Address: " + defaultShippingAddress +
                "\n}";
    }
    public void addOrder(Orders order){
        orderSet.add(order);
        order.setCustomer(this);
    }

}
