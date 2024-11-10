package migueldelgg.com.github.infra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import migueldelgg.com.github.useCases.ListAllRestaurantsUseCase;

import org.springframework.web.bind.annotation.GetMapping;

import migueldelgg.com.github.infra.dtos.CreateRestaurantDTO;
import migueldelgg.com.github.infra.entity.RestaurantEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import migueldelgg.com.github.infra.service.CreateRestaurantUseCaseImpl;

import org.springframework.web.bind.annotation.RequestParam;

import migueldelgg.com.github.infra.service.RestaurantDataUseCaseImpl;

@RestController
@RequestMapping("api/v1/restaurant")
public class RestaurantController {
    
    @Autowired
    private ListAllRestaurantsUseCase listAllRestaurantsUseCase;

    @Autowired
    private CreateRestaurantUseCaseImpl createRestaurantUseCase;

    @Autowired
    private RestaurantDataUseCaseImpl restaurantDataUseCaseImpl;

    @GetMapping("/all")
    public ResponseEntity<List<RestaurantEntity>> listAllRestaurants() {
        var response = listAllRestaurantsUseCase.execute();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/")
    public ResponseEntity<Void> create(@RequestBody CreateRestaurantDTO dto) throws Exception {
        createRestaurantUseCase.execute(dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<Object> getRestaurantDetails(@RequestParam String name) throws Exception {
        var body = restaurantDataUseCaseImpl.execute(name);
        return ResponseEntity.ok(body);
    }
}
