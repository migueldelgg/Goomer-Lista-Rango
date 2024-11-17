package migueldelgg.com.github.infra.service;

import migueldelgg.com.github.core.exception.RestaurantNotFoundException;
import migueldelgg.com.github.infra.dtos.ChangeRestaurantDataRequestDTO;
import migueldelgg.com.github.infra.dtos.ChangeRestaurantDataResponseDTO;
import migueldelgg.com.github.infra.dtos.ViaCepResponse;
import migueldelgg.com.github.infra.entity.AddressEntity;
import migueldelgg.com.github.infra.entity.OperationHoursEntity;
import migueldelgg.com.github.infra.entity.RestaurantEntity;
import migueldelgg.com.github.infra.repository.AddresEntityRepository;
import migueldelgg.com.github.infra.repository.OperationHoursEntityRepository;
import migueldelgg.com.github.infra.repository.RestaurantEntityRepository;
import migueldelgg.com.github.useCases.ChangeRestaurantDataUseCase;

import java.util.Optional;
import java.util.UUID;

public class ChangeRestaurantDataUseCaseImpl implements ChangeRestaurantDataUseCase {

    private final RestaurantEntityRepository restaurantRepo;
    private final AddresEntityRepository addressRepo;
    private final OperationHoursEntityRepository operationHoursRepo;
    private final ViaCepHttpCall viaCepHttpCall;

    public ChangeRestaurantDataUseCaseImpl(RestaurantEntityRepository restaurantRepo,
                                           AddresEntityRepository addressRepo,
                                           OperationHoursEntityRepository operationHoursRepo,
                                           ViaCepHttpCall viaCepHttpCall) {
        this.restaurantRepo = restaurantRepo;
        this.addressRepo = addressRepo;
        this.operationHoursRepo = operationHoursRepo;
        this.viaCepHttpCall = viaCepHttpCall;
    }

    @Override
    public ChangeRestaurantDataResponseDTO execute(
            ChangeRestaurantDataRequestDTO dto, String uuid)
    {
        ViaCepResponse viaCepResponse = viaCepHttpCall.execute(dto.getCep());
        String newAddress = viaCepResponse.street() + ", "+ dto.getNumber();

        RestaurantEntity restaurant = getRestaurantEntity(uuid);
        AddressEntity address = getAddressEntity(restaurant.getAddress().getId());
        OperationHoursEntity operationHours = getOperationHoursEntity(uuid);

        restaurant.setPhoto(dto.getRestaurantPhoto());

        address.setAddress(newAddress);
        address.setAddressComplement(dto.getAddressComplement());
        address.setCity(viaCepResponse.city());
        address.setState(viaCepResponse.state());
        address.setZipcode(viaCepResponse.cep());
        address.setCountry(dto.getCountry());

        operationHours.setDayOfWeekEnd(dto.getDayOfWeekEnd());
        operationHours.setDayOfWeekStart(dto.getDayOfWeekStart());
        operationHours.setEndTime(dto.getEndTime());
        operationHours.setStartTime(dto.getStartTime());

        restaurantRepo.save(restaurant);
        addressRepo.save(address);
        operationHoursRepo.save(operationHours);

        ChangeRestaurantDataResponseDTO response =
                new ChangeRestaurantDataResponseDTO("Atualizado com sucesso!");

        return response;
    }

    private RestaurantEntity getRestaurantEntity(String restaurantUUID) {
        return restaurantRepo.findById(UUID.fromString(restaurantUUID))
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurante não encontrado"));
    }

    private AddressEntity getAddressEntity(UUID addressUUID){
        return addressRepo.findById(addressUUID)
                        .orElseThrow(() -> new RestaurantNotFoundException("Endeço não encontrado"));
    }

    private OperationHoursEntity getOperationHoursEntity(String restaurantUUID){
        return operationHoursRepo.getOperationHoursByRestaurantId(UUID.fromString(restaurantUUID))
                .orElseThrow(() -> new RestaurantNotFoundException("Horario de funcionamento não encontrado"));
    }
}
