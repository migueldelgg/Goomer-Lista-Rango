package migueldelgg.com.github.infra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import migueldelgg.com.github.useCases.ListAllRestaurantsUseCase;

import org.springframework.web.bind.annotation.GetMapping;

import migueldelgg.com.github.infra.entity.RestaurantEntity;


@RestController
@RequestMapping("api/v1/restaurant")
public class RestaurantController {
    
    @Autowired
    private ListAllRestaurantsUseCase listAllRestaurantsUseCase;

    @GetMapping("/all")
    public ResponseEntity<List<RestaurantEntity>> listAllRestaurants() {
        
        var response = listAllRestaurantsUseCase.execute();
        
        return ResponseEntity.ok().body(response);

    }
    

}
