package migueldelgg.com.github.demo.restaurant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID>{
    
    @Query(value = "SELECT * FROM restaurant", nativeQuery = true)
    List<Restaurant> getAllRestaurants();

} 
