package migueldelgg.com.github.useCases;

import migueldelgg.com.github.infra.dtos.CreateRestaurantRequestBody;

public interface CreateRestaurantUseCase {
    public void execute(CreateRestaurantRequestBody requestBody) ;
}
