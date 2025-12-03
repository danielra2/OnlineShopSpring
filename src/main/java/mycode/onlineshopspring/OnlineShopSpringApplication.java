package mycode.onlineshopspring;

import mycode.onlineshopspring.view.View;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OnlineShopSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineShopSpringApplication.class, args);
    }
    @Bean
    @ConditionalOnProperty(name = "app.cli.enabled", havingValue = "true", matchIfMissing = true)
    CommandLineRunner show (View view){
        return args->{
            view.play();
//

        };
    }

}
