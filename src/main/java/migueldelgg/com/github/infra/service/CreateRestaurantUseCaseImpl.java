package migueldelgg.com.github.infra.service;

import org.springframework.stereotype.Service;

import migueldelgg.com.github.core.exception.SameDayException;
import migueldelgg.com.github.infra.dtos.CreateRestaurantRequestBody;
import migueldelgg.com.github.infra.entity.AddressEntity;
import migueldelgg.com.github.infra.entity.OperationHoursEntity;
import migueldelgg.com.github.infra.entity.RestaurantEntity;
import migueldelgg.com.github.infra.repository.AddresEntityRepository;
import migueldelgg.com.github.infra.repository.OperationHoursEntityRepository;
import migueldelgg.com.github.infra.repository.RestaurantEntityRepository;
import migueldelgg.com.github.useCases.CreateRestaurantUseCase;

@Service
public class CreateRestaurantUseCaseImpl implements CreateRestaurantUseCase {

    private final RestaurantEntityRepository restaurantRepo;
    private final AddresEntityRepository addressRepo;
    private final OperationHoursEntityRepository operationHoursRepo;
    private final ViaCepHttpCall viaCepHttpCall;

    public CreateRestaurantUseCaseImpl(AddresEntityRepository addressRepo, 
        OperationHoursEntityRepository operationHoursRepo, 
        RestaurantEntityRepository restaurantRepo, 
        migueldelgg.com.github.infra.service.ViaCepHttpCall viaCepHttpCall) {
            this.addressRepo = addressRepo;
            this.operationHoursRepo = operationHoursRepo;
            this.restaurantRepo = restaurantRepo;
            this.viaCepHttpCall = viaCepHttpCall;
    }

    @Override
    public void execute(CreateRestaurantRequestBody requestBody) {

        var viaCepResult = viaCepHttpCall.execute(requestBody.cep());
        String addressConstructor = viaCepResult.street() + ", "+ requestBody.number();
        
        var address = AddressEntity.builder()
            .address(addressConstructor)
            .addressComplement(requestBody.addressComplement())
            .city(viaCepResult.city())
            .state(viaCepResult.state())
            .country(requestBody.country())
            .zipcode(viaCepResult.cep())
            .build();

        var restaurant = RestaurantEntity.builder()
            .name(requestBody.restaurantName())
            .photo(requestBody.restaurantPhoto())
            .address(address)
            .build();

        var operation = OperationHoursEntity.builder()
            .restaurant(restaurant)
            .dayOfWeekStart(requestBody.dayOfWeekStart())
            .dayOfWeekEnd(requestBody.dayOfWeekEnd())
            .startTime(requestBody.startTime())
            .endTime(requestBody.endTime())
            .isEnabled(true)
            .build();

        addressRepo.saveAndFlush(address);
        restaurantRepo.saveAndFlush(restaurant);
        operationHoursRepo.saveAndFlush(operation);

        System.out.println("address => " + address);
        System.out.println("restaurant => " + restaurant);
        System.out.println("operation => " + operation);
    }

    public void validateRestaurant(CreateRestaurantRequestBody dto) {
        var restFromRepository = restaurantRepo.getRestaurantByName(dto.restaurantName());

        String exMessage = dto.dayOfWeekStart().equals(dto.dayOfWeekEnd())
                ? "O dia de início e o dia final não podem ser iguais."
                : dto.restaurantName()
                .equals(restFromRepository)
                ? "Esse nome de restaurante não está disponível."
                : !dto.startTime().isBefore(dto.endTime())
                ? "Horário de funcionamento inválido."
                : null;

        if (exMessage != null) {
            throw new SameDayException(exMessage);
        }
    }
}
