package migueldelgg.com.github.core.domain;

import java.time.Instant;
import java.util.UUID;

public record ProductPromotion(UUID id, String description, Double promotionPrice, 
    String dayOfWeekStart, String dayOfWeekEnd, 
    Instant startTime, Instant endTime) {
    
}
