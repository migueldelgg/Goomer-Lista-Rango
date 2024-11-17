package migueldelgg.com.github.infra.dtos;

import java.time.LocalTime;

import jakarta.validation.constraints.Pattern;
import migueldelgg.com.github.infra.entity.WeekDay;

public record CreateRestaurantRequestBody(
    String restaurantName,
    String restaurantPhoto,
    String addressComplement,
    String number,
    @Pattern(regexp = "\\d{8}", message = "O CEP deve conter exatamente 8 dígitos numéricos")
    String cep,
    String country,
    WeekDay dayOfWeekStart,
    WeekDay dayOfWeekEnd,
    LocalTime startTime,
    LocalTime endTime) {
}
