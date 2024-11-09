package migueldelgg.com.github.infra.service;

import java.util.UUID;

import javax.swing.text.Utilities;

import org.hibernate.mapping.List;
import org.springframework.stereotype.Service;

import migueldelgg.com.github.core.exception.SameDayException;
import migueldelgg.com.github.infra.dtos.CreateRestaurantDTO;
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

    public CreateRestaurantUseCaseImpl(AddresEntityRepository addressRepo, 
        OperationHoursEntityRepository operationHoursRepo,
        RestaurantEntityRepository restaurantRepo) {
            this.addressRepo = addressRepo;
            this.operationHoursRepo = operationHoursRepo;
            this.restaurantRepo = restaurantRepo;
    }

    @Override
    public void createRestaurant(CreateRestaurantDTO dto) throws Exception {

        restaurantExist(dto);
        
        var address = AddressEntity.builder()
            .address(dto.address())
            .addressComplement(dto.addressComplement())
            .city(dto.city())
            .state(dto.state())
            .country(dto.country())
            .zipcode(dto.zipcode())
            .build();
        addressRepo.save(address);

        var restaurant = RestaurantEntity.builder().id(UUID.randomUUID())
            .name(dto.restaurantName())
            .photo(dto.restaurantPhoto())
            .address(address)
            .build();
        restaurantRepo.save(restaurant);

        var operation = OperationHoursEntity.builder()
            .restaurant(restaurant)
            .dayOfWeekStart(dto.dayOfWeekStart())
            .dayOfWeekEnd(dto.dayOfWeekEnd())
            .startTime(dto.startTime())
            .endTime(dto.endTime())
            .isEnabled(true)
            .build();
        operationHoursRepo.save(operation);
    }

    public void restaurantExist(CreateRestaurantDTO dto){
        String exMessage = dto.dayOfWeekStart().equals(dto.dayOfWeekEnd()) 
            ? "O dia de início e o dia final não podem ser iguais."
            : dto.restaurantName()
                .equals(restaurantRepo.getRestaurantByName(dto.restaurantName()))
            ? "Esse nome de restaurante não está disponível."
            : null;

        if (exMessage != null) {
            throw new SameDayException(exMessage);
        }
    }
}
