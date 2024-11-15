package migueldelgg.com.github.infra.dtos;

import java.time.LocalTime;

import migueldelgg.com.github.infra.entity.WeekDay;

public record CreateRestaurantRequestBody(String restaurantName, String restaurantPhoto,
    String addressComplement,
    String number, String cep,
    String country,
    WeekDay dayOfWeekStart, WeekDay dayOfWeekEnd,
    LocalTime startTime, LocalTime endTime) {
}
