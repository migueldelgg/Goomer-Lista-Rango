package migueldelgg.com.github.core.exception;

public class RestaurantNotFoundException extends Exception{
    public RestaurantNotFoundException() {
        super();
    }
    public RestaurantNotFoundException(String message) {
        super(message); 
    }
}
