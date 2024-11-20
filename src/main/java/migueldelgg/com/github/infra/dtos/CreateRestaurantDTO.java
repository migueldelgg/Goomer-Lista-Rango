package migueldelgg.com.github.infra.dtos;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalTime;

import migueldelgg.com.github.infra.entity.WeekDay;

public record CreateRestaurantDTO(
        String restaurantName,
        String restaurantPhoto,
        String address,
        String addressComplement,
        String city,
        String state,
        String country,
        @Pattern(regexp = "\\d{8}", message = "O CEP deve conter exatamente 8 dígitos numéricos")
        String zipcode,
        WeekDay dayOfWeekStart,
        WeekDay dayOfWeekEnd,
        LocalTime startTime,
        LocalTime endTime
) {
    public CreateRestaurantDTO {
        if (zipcode != null && (zipcode.length() < 8 || zipcode.length() > 8)) {
            throw new IllegalArgumentException("O CEP deve ter exatamente 8 caracteres.");
        }
    }
}
