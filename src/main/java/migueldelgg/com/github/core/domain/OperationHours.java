package migueldelgg.com.github.core.domain;

import java.time.LocalTime;
import java.util.UUID;

public record OperationHours(UUID id, String dayOfWeekStart, String dayOfWeekEnd, 
LocalTime startTime, LocalTime endTime, boolean isEnabled) {
}
