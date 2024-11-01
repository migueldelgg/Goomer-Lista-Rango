package migueldelgg.com.github.demo.restaurant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListAllRestaurantsUseCase {
    
    @Autowired
    private RestaurantRepository repository;

    public List<Restaurant> execute() {

        List<Restaurant> response = repository.getAllRestaurants();

        System.out.println("LOGG "+ response);

        if(response.isEmpty()) {
            return null;
        }

        return response;
    }
}
