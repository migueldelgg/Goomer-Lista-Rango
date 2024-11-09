package migueldelgg.com.github.infra.service;

import org.springframework.stereotype.Service;

import migueldelgg.com.github.infra.dtos.RestaurantDataDTO;
import migueldelgg.com.github.infra.repository.RestaurantEntityRepository;
import migueldelgg.com.github.useCases.RestaurantDataUseCase;

@Service
public class RestaurantDataUseCaseImpl implements RestaurantDataUseCase{
    private final RestaurantEntityRepository repository;
    
    public RestaurantDataUseCaseImpl(RestaurantEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Object execute(String restaurant) {

        var responseFromRepo = repository.getRestaurantDataByName(restaurant);

        System.out.println("O banco trouxe! Veja: "+ responseFromRepo);

        return responseFromRepo;
    }
}
