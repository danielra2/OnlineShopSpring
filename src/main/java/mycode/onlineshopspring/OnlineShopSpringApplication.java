package mycode.onlineshopspring;

import mycode.onlineshopspring.customer.models.Customer;
import mycode.onlineshopspring.customer.repository.CustomerRepository;
import mycode.onlineshopspring.orderDetails.OrderDetails;
import mycode.onlineshopspring.orderDetails.repository.OrderDetailsRepository;
import org.springframework.aop.TargetSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class OnlineShopSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineShopSpringApplication.class, args);
    }
    @Bean
    CommandLineRunner show (CustomerRepository customerRepository, OrderDetailsRepository orderDetailsRepository){
        return args->{
//            System.out.println("======");
//            List<Customer> customerList=customerRepository.findAll();
//            customerList.stream().forEach(System.out::println);

            System.out.println("ORDER DETAILS");
            List<OrderDetails>orderDetails=orderDetailsRepository.findAll();
            orderDetails.stream().forEach(System.out::println);

        };
    }

}
