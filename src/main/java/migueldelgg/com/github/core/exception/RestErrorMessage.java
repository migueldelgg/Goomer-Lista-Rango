package migueldelgg.com.github.core.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RestErrorMessage (
    @JsonProperty("status_code") int statusCode, // Exibido como "status_code" no JSON
    String message
) {
}
