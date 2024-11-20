package migueldelgg.com.github.infra.service;

import migueldelgg.com.github.infra.repository.RestaurantEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RestaurantValidationService {
    private final RestaurantEntityRepository restaurantRepo;
    private final Logger log = LoggerFactory.getLogger(RestaurantValidationService.class);

    public RestaurantValidationService(RestaurantEntityRepository restaurantRepo) {
        this.restaurantRepo = restaurantRepo;
    }

    public Boolean restaurantNameAlreadyExist(String restaurantName) {
        boolean exists = restaurantRepo.getRestaurantByName(restaurantName) != null;
        if (exists) {
            log.warn("Restaurant name '{}' already exists in the database.", restaurantName);
        }
        return exists;
    }

}
