package mycode.onlineshopspring;

import mycode.onlineshopspring.view.View;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineShopSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineShopSpringApplication.class, args);
    }
    @Bean
    CommandLineRunner show (View view){
        return args->{
            view.play();
//

        };
    }

}
