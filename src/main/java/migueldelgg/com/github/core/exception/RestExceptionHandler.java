package migueldelgg.com.github.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
    
    @ExceptionHandler(SameDayException.class)
    public ResponseEntity<RestErrorMessage> handleSameDayException(SameDayException ex) {

        RestErrorMessage response = new RestErrorMessage(400,
            ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<RestErrorMessage> handleRestaurantNotFoundException(RestaurantNotFoundException ex) {

        RestErrorMessage response = new RestErrorMessage(404,
            ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
