package migueldelgg.com.github.infra.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import migueldelgg.com.github.infra.entity.RestaurantEntity;
import migueldelgg.com.github.infra.projections.RestaurantDataProjection;

@Repository
public interface RestaurantEntityRepository extends JpaRepository<RestaurantEntity, UUID>{
    
    @Query(value = "SELECT * FROM restaurant", nativeQuery = true)
    List<RestaurantEntity> getAllRestaurants();

    @Query(value = "SELECT name FROM restaurant WHERE name = :inputName", nativeQuery = true)
    String getRestaurantByName(String inputName);

    @Query(value = "SELECT name FROM restaurant WHERE id = :restaurantId", nativeQuery = true)
    String getRestaurantNameById(UUID restaurantId);
    
    @Query(value = "SELECT rest.name AS restaurantName, " +
       "rest.photo as restaurantPhoto, adr.address AS restaurantAddress, " +
       "adr.address_complement AS restaurantAddressComplement, " +
       "adr.city AS city, adr.state AS state, adr.country AS country, " +
       "adr.zip_code AS zipCode FROM restaurant AS rest " +
       "INNER JOIN address AS adr ON rest.address_id = adr.id " +
       "WHERE rest.name = :inputName", nativeQuery = true)
    RestaurantDataProjection getRestaurantDataByName(@Param("inputName") String inputName);

    @Query(value = "SELECT rest.name AS restaurantName, " +
       "rest.photo as restaurantPhoto, adr.address AS restaurantAddress, " +
       "adr.address_complement AS restaurantAddressComplement, " +
       "adr.city AS city, adr.state AS state, adr.country AS country, " +
       "adr.zip_code AS zipCode FROM restaurant AS rest " +
       "INNER JOIN address AS adr ON rest.address_id = adr.id " +
       "WHERE rest.id = :restaurantId", nativeQuery = true)
    RestaurantDataProjection getRestaurantDataById(@Param("restaurantId") UUID restaurantId);
}
