package migueldelgg.com.github.useCases;

import migueldelgg.com.github.infra.projections.RestaurantDataProjection;

public interface RestaurantDataUseCase {
    public RestaurantDataProjection execute(String uuid) ;    

    public void restaurantExist(String uuid);
}
