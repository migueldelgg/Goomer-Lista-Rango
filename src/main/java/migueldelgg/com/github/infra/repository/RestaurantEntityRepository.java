package migueldelgg.com.github.infra.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import migueldelgg.com.github.infra.entity.RestaurantEntity;

@Repository
public interface RestaurantEntityRepository extends JpaRepository<RestaurantEntity, UUID>{
    
    @Query(value = "SELECT * FROM restaurant", nativeQuery = true)
    List<RestaurantEntity> getAllRestaurants();

    @Query(value = "SELECT name FROM restaurant WHERE name = :inputName", nativeQuery = true)
    String getRestaurantByName(String inputName);
}
