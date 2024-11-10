package migueldelgg.com.github.infra.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcAffordanceBuilderDsl;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

import jakarta.persistence.Entity;
import migueldelgg.com.github.infra.projections.RestaurantDataProjection;
import migueldelgg.com.github.infra.service.RestaurantDataUseCaseImpl;

@RestController
@RequestMapping("api/v1/restaurants")
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
        for(RestaurantEntity restaurant : response) {
            restaurant.add(linkTo(methodOn(RestaurantController.class)
                .getRestaurantDetails(restaurant.getName()))
                .withSelfRel());
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/")
    public ResponseEntity<Void> create(@RequestBody CreateRestaurantDTO dto) throws Exception {
        createRestaurantUseCase.execute(dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<EntityModel<RestaurantDataProjection>> getRestaurantDetails(@RequestParam String name) {
        var body = restaurantDataUseCaseImpl.execute(name);
        EntityModel<RestaurantDataProjection> model = EntityModel.of(body);
        model.add(linkTo(methodOn(RestaurantController.class)
                .getRestaurantDetails(body.getRestaurantName()))
                .withSelfRel());
        model.add(linkTo(methodOn(RestaurantController.class)
                .listAllRestaurants())
                .withRel(IanaLinkRelations.COLLECTION));
        return ResponseEntity.ok(model);
    }
}
