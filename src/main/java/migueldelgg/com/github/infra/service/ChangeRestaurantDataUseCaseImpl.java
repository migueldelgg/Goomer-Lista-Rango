package migueldelgg.com.github.infra.service;

import migueldelgg.com.github.core.exception.RestaurantNotFoundException;
import migueldelgg.com.github.core.exception.SameDayException;
import migueldelgg.com.github.infra.dtos.ChangeRestaurantDataRequestDTO;
import migueldelgg.com.github.infra.dtos.ChangeRestaurantDataResponseDTO;
import migueldelgg.com.github.infra.dtos.CreateRestaurantRequestBody;
import migueldelgg.com.github.infra.dtos.ViaCepResponse;
import migueldelgg.com.github.infra.entity.AddressEntity;
import migueldelgg.com.github.infra.entity.OperationHoursEntity;
import migueldelgg.com.github.infra.entity.RestaurantEntity;
import migueldelgg.com.github.infra.repository.AddresEntityRepository;
import migueldelgg.com.github.infra.repository.OperationHoursEntityRepository;
import migueldelgg.com.github.infra.repository.RestaurantEntityRepository;
import migueldelgg.com.github.infra.utils.ValidationUtils;
import migueldelgg.com.github.useCases.ChangeRestaurantDataUseCase;

import java.util.Optional;
import java.util.UUID;

public class ChangeRestaurantDataUseCaseImpl implements ChangeRestaurantDataUseCase {

    private final RestaurantEntityRepository restaurantRepo;
    private final AddresEntityRepository addressRepo;
    private final OperationHoursEntityRepository operationHoursRepo;
    private final ViaCepHttpCall viaCepHttpCall;
    private final RestaurantValidationService validationService;

    public ChangeRestaurantDataUseCaseImpl(RestaurantEntityRepository restaurantRepo, AddresEntityRepository addressRepo, OperationHoursEntityRepository operationHoursRepo, ViaCepHttpCall viaCepHttpCall, RestaurantValidationService validationService) {
        this.restaurantRepo = restaurantRepo;
        this.addressRepo = addressRepo;
        this.operationHoursRepo = operationHoursRepo;
        this.viaCepHttpCall = viaCepHttpCall;
        this.validationService = validationService;
    }

    /*Refatorar depois, se eu passar null como ficaria o validateRestaurant? */
    @Override
    public ChangeRestaurantDataResponseDTO execute(
            ChangeRestaurantDataRequestDTO requestBody, String uuid) {
        validateRestaurant(requestBody);

        // Buscar os dados existentes
        RestaurantEntity restaurant = getRestaurantEntity(uuid);
        AddressEntity address = getAddressEntity(restaurant.getAddress().getId());
        OperationHoursEntity operationHours = getOperationHoursEntity(uuid);

        // Atualizar apenas os campos preenchidos no DTO
        if (requestBody.getRestaurantPhoto() != null) {
            restaurant.setPhoto(requestBody.getRestaurantPhoto());
        }

        if (requestBody.getAddressComplement() != null) {
            address.setAddressComplement(requestBody.getAddressComplement());
        }
        if (requestBody.getNumber() != null || requestBody.getCep() != null) {
            ViaCepResponse viaCepResponse = viaCepHttpCall.execute(requestBody.getCep());
            String newAddress = viaCepResponse.street() + ", " + requestBody.getNumber();
            address.setAddress(newAddress);
            address.setCity(viaCepResponse.city());
            address.setState(viaCepResponse.state());
            address.setZipcode(viaCepResponse.cep());
            address.setCountry(requestBody.getCountry());
        }

        if (requestBody.getDayOfWeekStart() != null && requestBody.getDayOfWeekEnd() != null) {
            operationHours.setDayOfWeekStart(requestBody.getDayOfWeekStart());
            operationHours.setDayOfWeekEnd(requestBody.getDayOfWeekEnd());
        }

        if (requestBody.getStartTime() != null && requestBody.getEndTime() != null) {
            operationHours.setStartTime(requestBody.getStartTime());
            operationHours.setEndTime(requestBody.getEndTime());
        }

        // Salvar entidades
        restaurantRepo.save(restaurant);
        addressRepo.save(address);
        operationHoursRepo.save(operationHours);

        return new ChangeRestaurantDataResponseDTO("Atualizado com sucesso!");
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

    public void validateRestaurant(ChangeRestaurantDataRequestDTO dto) {
        String errorMessage = null;

        if(ValidationUtils.isTheSameWeekDay(dto.getDayOfWeekStart(), dto.getDayOfWeekEnd()))
            errorMessage = "O dia de início e o dia final não podem ser iguais.";

        if(ValidationUtils.endTimeIsBeforeStartTime(dto.getStartTime(), dto.getEndTime()))
            errorMessage = "Horário de funcionamento inválido";

        if (errorMessage != null) {
            throw new SameDayException(errorMessage);
        }
    }
}
