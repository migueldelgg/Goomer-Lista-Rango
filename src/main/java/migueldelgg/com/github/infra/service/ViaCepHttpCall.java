package migueldelgg.com.github.infra.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import migueldelgg.com.github.core.exception.ViaCepException;
import migueldelgg.com.github.infra.dtos.ViaCepResponse;

@Service
public class ViaCepHttpCall {
    
    public ViaCepResponse execute(String cep) {
        String url = String.format("https://brasilapi.com.br/api/cep/v1/%s", cep);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

            return convertToViaCepResponse(response);
        } catch (IOException | InterruptedException e) {
            throw new ViaCepException("Error while calling ViaCep API", e);
        }
    }

    public static ViaCepResponse convertToViaCepResponse(HttpResponse<String> response) {
        if (response.statusCode() == 404) {
            throw new IllegalArgumentException("CEP not found");
        }

        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
        String cep = jsonObject.get("cep").getAsString();
        String state = jsonObject.get("state").getAsString();
        String city = jsonObject.get("city").getAsString();
        String neighborhood = jsonObject.get("neighborhood").getAsString();
        String street = jsonObject.get("street").getAsString();
        String service = jsonObject.get("service").getAsString();
        
        return new ViaCepResponse(cep, state, city, neighborhood, street, service);
    }

}
