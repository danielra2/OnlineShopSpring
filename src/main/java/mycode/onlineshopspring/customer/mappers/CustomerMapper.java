package mycode.onlineshopspring.customer.mappers;

import mycode.onlineshopspring.customer.dto.CustomerDto;
import mycode.onlineshopspring.customer.models.Customer;

import java.util.Objects;

public class CustomerMapper {


    private static String nvl(String s) { return s == null ? "" : s; }
    private static String trim(String s) { return s == null ? null : s.trim(); }

    public Customer mapCustomerDtoToCustomer(CustomerDto dto){
        Objects.requireNonNull("Customer DTO is null");
        Customer e=new Customer();
        e.setBillingAddress(trim(dto.billingAddress()));
        e.setCountry(trim(dto.country()));
        e.setEmail(trim(dto.email()));
        e.setPhone(trim(dto.phone()));
        e.setPassword(trim(dto.password()));
        e.setDefaultShippingAddress(trim(dto.defaultShippingAddress()));
        e.setFullName(trim(dto.fullName()));
        return e;
    }


}
