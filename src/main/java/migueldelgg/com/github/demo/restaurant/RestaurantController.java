package migueldelgg.com.github.demo.restaurant;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {
    
    @Autowired
    private ListAllRestaurantsUseCase service;

    @GetMapping("/get-restaurants")
    public ResponseEntity<List<Restaurant>> execute() {
        var response = service.execute();

        if (response == null) {
            return ResponseEntity.noContent().build(); // Retorna status 204 (No Content)
        }

        // Retorna a lista de restaurantes com o status 200 (OK)
        return ResponseEntity.ok(response);
    }   
}
