package migueldelgg.com.github.core.exception;

public class RestaurantNotFoundException extends RuntimeException{
    public RestaurantNotFoundException() {
        super();
    }
    public RestaurantNotFoundException(String message) {
        super(message); 
    }
}
