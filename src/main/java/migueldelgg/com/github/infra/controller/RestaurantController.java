package migueldelgg.com.github.infra.controller;

import java.util.List;
import java.util.UUID;

import migueldelgg.com.github.infra.dtos.ChangeRestaurantDataRequestDTO;
import migueldelgg.com.github.infra.dtos.ChangeRestaurantDataResponseDTO;
import migueldelgg.com.github.infra.service.ChangeRestaurantDataUseCaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import migueldelgg.com.github.infra.dtos.CreateRestaurantRequestBody;
import migueldelgg.com.github.infra.entity.RestaurantEntity;
import migueldelgg.com.github.infra.projections.RestaurantDataProjection;
import migueldelgg.com.github.infra.service.CreateRestaurantUseCaseImpl;
import migueldelgg.com.github.infra.service.RestaurantDataUseCaseImpl;
import migueldelgg.com.github.useCases.ListAllRestaurantsUseCase;

@RestController
@RequestMapping("api/v1/restaurants")
public class RestaurantController {
    @Autowired
    private ListAllRestaurantsUseCase listAllRestaurantsUseCase;

    @Autowired
    private CreateRestaurantUseCaseImpl createRestaurantUseCase;

    @Autowired
    private RestaurantDataUseCaseImpl restaurantDataUseCaseImpl;

    @Autowired
    private ChangeRestaurantDataUseCaseImpl changeRestaurantDataUseCase;

    @GetMapping
    public ResponseEntity<List<RestaurantEntity>> listAllRestaurants() {
        var response = listAllRestaurantsUseCase.execute();
        for (RestaurantEntity restaurant : response) {
            restaurant.add(linkTo(methodOn(RestaurantController.class)
                    .getRestaurantDetails(restaurant.getId().toString()))
                    .withSelfRel());
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/")
    public ResponseEntity<Void> create(@RequestBody CreateRestaurantRequestBody dto) {
        createRestaurantUseCase.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<EntityModel<RestaurantDataProjection>> getRestaurantDetails(@PathVariable String uuid) {
        var body = restaurantDataUseCaseImpl.execute(uuid);
        EntityModel<RestaurantDataProjection> model = EntityModel.of(body);
        model.add(linkTo(methodOn(RestaurantController.class)
                .getRestaurantDetails(uuid))
                .withSelfRel());
        model.add(linkTo(methodOn(RestaurantController.class)
                .listAllRestaurants())
                .withRel(IanaLinkRelations.COLLECTION));
        return ResponseEntity.ok(model);
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<ChangeRestaurantDataResponseDTO> changeRestaurantData(
            @PathVariable String uuid, @RequestBody
            ChangeRestaurantDataRequestDTO requestBody
    ) {
        var response = changeRestaurantDataUseCase.execute(requestBody, uuid);
        return ResponseEntity.ok().body(response);
    }
}