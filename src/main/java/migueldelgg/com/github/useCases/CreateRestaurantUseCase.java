package migueldelgg.com.github.useCases;

import migueldelgg.com.github.infra.dtos.CreateRestaurantDTO;

public interface CreateRestaurantUseCase {
    public void createRestaurant(CreateRestaurantDTO dto) throws Exception;
}
