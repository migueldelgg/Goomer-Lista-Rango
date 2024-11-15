package migueldelgg.com.github.infra.dtos;

import java.time.LocalTime;

import migueldelgg.com.github.infra.entity.WeekDay;

public record CreateRestaurantDTO(String restaurantName, String restaurantPhoto, 
    String address, String addressComplement,
    String city, String state, String country, String zipcode,
    WeekDay dayOfWeekStart, WeekDay dayOfWeekEnd,
    LocalTime startTime, LocalTime endTime) {
}
