package migueldelgg.com.github.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import migueldelgg.com.github.infra.repository.AddresEntityRepository;
import migueldelgg.com.github.infra.repository.OperationHoursEntityRepository;
import migueldelgg.com.github.infra.repository.RestaurantEntityRepository;
import migueldelgg.com.github.infra.service.CreateRestaurantUseCaseImpl;
import migueldelgg.com.github.infra.service.ListAllRestaurantsUseCaseImpl;
import migueldelgg.com.github.useCases.CreateRestaurantUseCase;
import migueldelgg.com.github.useCases.ListAllRestaurantsUseCase;

@Configuration
public class RestaurantConfig {

    @Bean
    public ListAllRestaurantsUseCase listAllRestaurantsUseCase(RestaurantEntityRepository repository) {
        return new ListAllRestaurantsUseCaseImpl(repository);
    }

    @Bean
    public CreateRestaurantUseCase createRestaurantUseCase(
            AddresEntityRepository addressRepository,
            OperationHoursEntityRepository operationHoursRepository,
            RestaurantEntityRepository restaurantRepository) {
        return new CreateRestaurantUseCaseImpl(addressRepository, operationHoursRepository, restaurantRepository, null);
    }

}