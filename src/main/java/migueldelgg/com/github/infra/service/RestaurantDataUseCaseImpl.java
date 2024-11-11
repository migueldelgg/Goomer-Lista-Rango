package migueldelgg.com.github.infra.service;

import java.util.UUID;

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
    public RestaurantDataProjection execute(String uuid) {
        restaurantExist(uuid);
        UUID restId = UUID.fromString(uuid);
        var responseFromRepo = repository.getRestaurantDataById(restId);

        System.out.println("O uuid e esse => "+ uuid);
        System.out.println("O responseFromRepo e esse => "+ responseFromRepo);
        return responseFromRepo;
    }

    @Override
    public void restaurantExist(String uuid) {
        UUID id = UUID.fromString(uuid);
        var restaurantData = repository.getRestaurantNameById(id);
        System.out.println("O uuid e esse => "+ id);
        System.out.println("O responseFromRepo e esse => "+ restaurantData);
        if (restaurantData.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurante não encontrado.");
        }
    }
}
