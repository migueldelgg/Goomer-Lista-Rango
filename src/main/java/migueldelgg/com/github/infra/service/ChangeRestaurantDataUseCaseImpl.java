package migueldelgg.com.github.infra.service;

import migueldelgg.com.github.core.exception.RestaurantNotFoundException;
import migueldelgg.com.github.core.exception.SameDayException;
import migueldelgg.com.github.infra.dtos.ChangeRestaurantDataRequestDTO;
import migueldelgg.com.github.infra.dtos.ChangeRestaurantDataResponseDTO;
import migueldelgg.com.github.infra.dtos.ViaCepResponse;
import migueldelgg.com.github.infra.entity.AddressEntity;
import migueldelgg.com.github.infra.entity.OperationHoursEntity;
import migueldelgg.com.github.infra.entity.RestaurantEntity;
import migueldelgg.com.github.infra.repository.AddresEntityRepository;
import migueldelgg.com.github.infra.repository.OperationHoursEntityRepository;
import migueldelgg.com.github.infra.repository.RestaurantEntityRepository;
import migueldelgg.com.github.infra.utils.ValidationUtils;
import migueldelgg.com.github.useCases.ChangeRestaurantDataUseCase;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.UUID;

@Service
public class ChangeRestaurantDataUseCaseImpl implements ChangeRestaurantDataUseCase {

    private final RestaurantEntityRepository restaurantRepo;
    private final OperationHoursEntityRepository operationHoursRepo;

    public ChangeRestaurantDataUseCaseImpl(RestaurantEntityRepository restaurantRepo, OperationHoursEntityRepository operationHoursRepo) {
        this.restaurantRepo = restaurantRepo;
        this.operationHoursRepo = operationHoursRepo;
    }

    /*Refatorar depois, se eu passar null como ficaria o validateRestaurant? */
    @Override
    public ChangeRestaurantDataResponseDTO execute(
            ChangeRestaurantDataRequestDTO requestBody, String uuid) {
        validateRestaurant(requestBody);

        // Buscar os dados existentes
        System.out.println("FOTO FIELD =>" + requestBody.getPhoto());

        RestaurantEntity restaurant = getRestaurantEntity(uuid);
        OperationHoursEntity operationHours = getOperationHoursEntity(uuid);

        updateEntityFromRequest(requestBody, restaurant);
        updateEntityFromRequest(requestBody, operationHours);

        // Salvar entidades
        restaurantRepo.save(restaurant);
        operationHoursRepo.save(operationHours);

        return new ChangeRestaurantDataResponseDTO("Atualizado com sucesso!");
    }


    private RestaurantEntity getRestaurantEntity(String restaurantUUID) {
        return restaurantRepo.findById(UUID.fromString(restaurantUUID))
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurante não encontrado"));
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

    public void updateEntityFromRequest(Object source, Object target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Fonte e destino não podem ser nulos");
        }

        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true); // Permite acesso a campos privados
            try {
                Object value = field.get(source);
                if (value != null) { // Apenas atualiza se o campo não for nulo
                    Field targetField = target.getClass().getDeclaredField(field.getName());
                    targetField.setAccessible(true);
                    System.out.println("value => "+ value);
                    System.out.println("target => "+ target);
                    System.out.println("targetField => "+ targetField);
                    targetField.set(target, value);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.out.println("Exception capturada");
            }
        }

    }

}
