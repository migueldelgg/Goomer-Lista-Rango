package migueldelgg.com.github.useCases;

import java.util.List;

import migueldelgg.com.github.infra.entity.RestaurantEntity;

public interface ListAllRestaurantsUseCase {
    public List<RestaurantEntity> execute();
}
