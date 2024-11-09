package migueldelgg.com.github.infra.service;

import java.util.List;

import org.springframework.stereotype.Service;

import migueldelgg.com.github.infra.entity.RestaurantEntity;
import migueldelgg.com.github.infra.repository.RestaurantEntityRepository;
import migueldelgg.com.github.infra.utils.Utilities;
import migueldelgg.com.github.useCases.ListAllRestaurantsUseCase;

@Service
public class ListAllRestaurantsUseCaseImpl implements ListAllRestaurantsUseCase {

    private final RestaurantEntityRepository repository;
    
    public ListAllRestaurantsUseCaseImpl(RestaurantEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<RestaurantEntity> execute() {
        List<RestaurantEntity> response = repository.getAllRestaurants();
        Utilities.log.info("Lista de restaurantes => " + response);
        return response;
    }

}