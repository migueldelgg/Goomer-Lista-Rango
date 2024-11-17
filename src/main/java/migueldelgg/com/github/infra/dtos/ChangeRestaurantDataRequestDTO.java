package migueldelgg.com.github.infra.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import migueldelgg.com.github.infra.entity.WeekDay;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangeRestaurantDataRequestDTO {

    private String restaurantPhoto;
    private String addressComplement;
    private String number;
    private String cep;
    private String country;
    private WeekDay dayOfWeekStart;
    private WeekDay dayOfWeekEnd;
    private LocalTime startTime;
    private LocalTime endTime;
}
