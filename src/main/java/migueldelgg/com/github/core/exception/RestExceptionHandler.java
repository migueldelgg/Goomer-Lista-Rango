package migueldelgg.com.github.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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
    
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<RestErrorMessage> handleNoResourceFoundException(NoResourceFoundException ex) {

        RestErrorMessage response = new RestErrorMessage(404,
            ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestErrorMessage> handleIllegalArgumentException(IllegalArgumentException ex) {

        RestErrorMessage response = new RestErrorMessage(400,
            ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RestErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

        RestErrorMessage response = new RestErrorMessage(400,
            ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
