package migueldelgg.com.github.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import migueldelgg.com.github.infra.repository.RestaurantEntityRepository;
import migueldelgg.com.github.infra.service.ListAllRestaurantsUseCaseImpl;
import migueldelgg.com.github.useCases.ListAllRestaurantsUseCase;

@Configuration
public class RestaurantConfig {

    @Bean
    public ListAllRestaurantsUseCase listAllRestaurantsUseCase(RestaurantEntityRepository repository) {
        return new ListAllRestaurantsUseCaseImpl(repository);
    }

}