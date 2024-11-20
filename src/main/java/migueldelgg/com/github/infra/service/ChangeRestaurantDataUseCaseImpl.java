package migueldelgg.com.github.infra.service;

import migueldelgg.com.github.core.exception.RestaurantNotFoundException;
import migueldelgg.com.github.core.exception.SameDayException;
import migueldelgg.com.github.infra.dtos.ChangeRestaurantDataRequestDTO;
import migueldelgg.com.github.infra.dtos.ChangeRestaurantDataResponseDTO;
import migueldelgg.com.github.infra.entity.OperationHoursEntity;
import migueldelgg.com.github.infra.entity.RestaurantEntity;
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

        if(!ValidationUtils.isStartTimeAndEndTimeNull(dto.getStartTime(), dto.getEndTime())) {
            if(ValidationUtils.endTimeIsBeforeStartTime(dto.getStartTime(), dto.getEndTime()))
                errorMessage = "Horário de funcionamento inválido";
        }

        if(!ValidationUtils.isStartWeekDayAndEndWeekDayNull(dto.getDayOfWeekStart(), dto.getDayOfWeekEnd())) {
            if(ValidationUtils.isTheSameWeekDay(dto.getDayOfWeekStart(), dto.getDayOfWeekEnd()))
                errorMessage = "O dia de início e o dia final não podem ser iguais.";
        }

        if (errorMessage != null) {
            throw new SameDayException(errorMessage);
        }
    }

    public void updateEntityFromRequest(Object origem, Object destino) {
        if (origem == null || destino == null) {
            throw new IllegalArgumentException("Fonte e destino não podem ser nulos");
        }
        // Obtém todos os atributos declarados na classe do objeto "origem"
        Field[] fields = origem.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true); // Permite acesso a atributos privados do objeto "origem"
            try {
                // Acessa o valor do atributo representado por "field" no objeto "origem"
                System.out.println("Acessa o valor do atributo representado por \"field\" no objeto \"origem\" " + field.get(origem));
                Object value = field.get(origem);

                if (value != null) { // Verifica se o valor do atributo de "origem" não é nulo

                    // Localiza o campo correspondente na classe do objeto "destino" com o mesmo nome do atributo atual de "origem"
                    Field targetField = destino.getClass().getDeclaredField(field.getName());

                    // Torna acessível o atributo localizado no objeto "destino"
                    targetField.setAccessible(true);
                    System.out.println("value => " + value);
                    System.out.println("target => " + destino);
                    System.out.println("targetField => " + targetField);

                    // Atualiza o valor do atributo do objeto "destino" com o valor do atributo correspondente no objeto "origem"
                    targetField.set(destino, value);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Captura exceções caso o atributo não exista em "destino" ou não seja acessível
                System.out.println("Exception capturada");
            }
        }

    }

}
