package mycode.onlineshopspring.customer.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mycode.onlineshopspring.orders.models.Orders;


import java.util.HashSet;
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
    private Set<Orders>orderSet=new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getDefaultShippingAddress() {
        return defaultShippingAddress;
    }

    public void setDefaultShippingAddress(String defaultShippingAddress) {
        this.defaultShippingAddress = defaultShippingAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Orders> getOrderSet() {
        return orderSet;
    }

    public void setOrderSet(Set<Orders> orderSet) {
        this.orderSet = orderSet;
    }

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
