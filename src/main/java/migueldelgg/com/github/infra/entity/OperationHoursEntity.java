package migueldelgg.com.github.infra.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
@Table(name = "operation_hours")
public class OperationHoursEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id", nullable = false)
    private RestaurantEntity restaurant;

    @Enumerated(EnumType.STRING) // Mapeia o enum como string para corresponder ao tipo ENUM do PostgreSQL
    @Column(name = "day_of_week_start", nullable = false)
    private WeekDay dayOfWeekStart;

    @Enumerated(EnumType.STRING) // Mapeia o enum como string para corresponder ao tipo ENUM do PostgreSQL
    @Column(name = "day_of_week_end", nullable = false)
    private WeekDay dayOfWeekEnd;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;

    public OperationHoursEntity(UUID id, RestaurantEntity restaurant, WeekDay dayOfWeekStart, WeekDay dayOfWeekEnd,
            LocalTime startTime, LocalTime endTime, boolean isEnabled) {
        this.id = id;
        this.restaurant = restaurant;
        this.dayOfWeekStart = dayOfWeekStart;
        this.dayOfWeekEnd = dayOfWeekEnd;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isEnabled = isEnabled;
    }

    public OperationHoursEntity() {
    }
}
