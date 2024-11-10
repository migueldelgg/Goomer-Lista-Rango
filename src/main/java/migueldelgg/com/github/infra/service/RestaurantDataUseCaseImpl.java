package migueldelgg.com.github.infra.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import migueldelgg.com.github.core.exception.RestaurantNotFoundException;
import migueldelgg.com.github.infra.projections.RestaurantDataProjection;
import migueldelgg.com.github.infra.repository.RestaurantEntityRepository;
import migueldelgg.com.github.useCases.RestaurantDataUseCase;

@Service
public class RestaurantDataUseCaseImpl implements RestaurantDataUseCase{
    private final RestaurantEntityRepository repository;
    
    public RestaurantDataUseCaseImpl(RestaurantEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public RestaurantDataProjection execute(String restaurant) {
        restaurantExist(restaurant);
        var responseFromRepo = repository.getRestaurantDataByName(restaurant);
        System.out.println("O banco trouxe! Veja: "+ responseFromRepo);
        return responseFromRepo;
    }

    @Override
    public void restaurantExist(String restaurant) {
        var restaurantData = repository.getRestaurantByName(restaurant);
        if (restaurantData.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurante não encontrado.");
        }
    }
}
