package migueldelgg.com.github.useCases;

import migueldelgg.com.github.infra.dtos.CreateRestaurantDTO;

public interface CreateRestaurantUseCase {
    public void execute(CreateRestaurantDTO dto) throws Exception;
}
