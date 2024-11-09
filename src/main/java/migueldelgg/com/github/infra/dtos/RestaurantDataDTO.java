package migueldelgg.com.github.infra.dtos;

import migueldelgg.com.github.infra.entity.AddressEntity;
import migueldelgg.com.github.infra.entity.RestaurantEntity;

public record RestaurantDataDTO (RestaurantEntity restaurant,
     AddressEntity address){
}
