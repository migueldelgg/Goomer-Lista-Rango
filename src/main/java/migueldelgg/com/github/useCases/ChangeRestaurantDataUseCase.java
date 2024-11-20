package migueldelgg.com.github.useCases;

import migueldelgg.com.github.infra.dtos.ChangeRestaurantDataRequestDTO;
import migueldelgg.com.github.infra.dtos.ChangeRestaurantDataResponseDTO;

public interface ChangeRestaurantDataUseCase {

    // message => alterado com sucesso
    // href => pro restaurantDataDetails
    public ChangeRestaurantDataResponseDTO execute(ChangeRestaurantDataRequestDTO dto, String uuid);
}
