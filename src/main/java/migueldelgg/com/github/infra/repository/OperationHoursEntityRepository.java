package migueldelgg.com.github.infra.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import migueldelgg.com.github.infra.entity.OperationHoursEntity;

@Repository
public interface OperationHoursEntityRepository extends JpaRepository<OperationHoursEntity, UUID>{
    @Query(value = "SELECT * FROM operation_hours WHERE restaurant_id = :restaurantId", nativeQuery = true)
    Optional<OperationHoursEntity> getOperationHoursByRestaurantId(UUID restaurantId);

}
