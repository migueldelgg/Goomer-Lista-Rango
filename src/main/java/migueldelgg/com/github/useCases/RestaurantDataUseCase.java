package migueldelgg.com.github.useCases;

import java.util.Optional;

import migueldelgg.com.github.infra.projections.RestaurantDataProjection;

public interface RestaurantDataUseCase {
    public Optional<RestaurantDataProjection> execute(String restaurant) throws Exception;    
}
