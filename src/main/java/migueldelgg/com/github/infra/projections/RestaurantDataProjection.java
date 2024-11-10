package migueldelgg.com.github.infra.projections;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface RestaurantDataProjection {
    @JsonProperty("nome_restaurante")
    String getRestaurantName();
    @JsonProperty("foto_restaurante")
    String getRestaurantPhoto();
    @JsonProperty("endereco")
    String getRestaurantAddress();
    @JsonProperty("complemento")
    String getRestaurantAddressComplement();
    @JsonProperty("cidade")
    String getCity();
    @JsonProperty("estado")
    String getState();
    @JsonProperty("país")
    String getCountry();
    @JsonProperty("cep")
    String getZipCode();
}

