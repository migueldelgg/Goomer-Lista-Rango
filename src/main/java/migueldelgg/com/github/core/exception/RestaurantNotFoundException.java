package migueldelgg.com.github.core.exception;

public class RestaurantNotFoundException extends Exception{
    
    private String code;

    public RestaurantNotFoundException(String message, String code) {
        super(message);
        this.code = code;
    }

}
