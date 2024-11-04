package migueldelgg.com.github.core.domain;

import java.util.UUID;

public record Address(UUID id, String address, String addressComplement, 
    String city, String state, String country, String zipcode){
}
