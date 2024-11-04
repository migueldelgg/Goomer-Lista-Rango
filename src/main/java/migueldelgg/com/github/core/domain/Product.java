package migueldelgg.com.github.core.domain;

import java.util.UUID;

public record Product(UUID id, String name, Double price, 
    String photo) {
}
