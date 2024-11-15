package migueldelgg.com.github.infra.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import migueldelgg.com.github.infra.dtos.ViaCepResponse;

@Service
public class ViaCepHttpCall {
    
    public void execute(String cep) throws IOException, InterruptedException {
        String url = 
        String.format("https://brasilapi.com.br/api/cep/v1/%s",
        cep);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
            .send(request, HttpResponse.BodyHandlers.ofString());

        var objeto = convertToViaCepResponse(response);

        System.out.println("Response => "+ objeto);
    }

    public static ViaCepResponse convertToViaCepResponse(HttpResponse<String> response) {
        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
        var cep = jsonObject.get("cep").getAsString();
        var state = jsonObject.get("state").getAsString();
        var city = jsonObject.get("city").getAsString();
        var neighborhood = jsonObject.get("neighborhood").getAsString();
        var street = jsonObject.get("street").getAsString();
        var service = jsonObject.get("service").getAsString();

        ViaCepResponse viaCepResponse = new ViaCepResponse(cep, state, city, neighborhood, street, service);
        return viaCepResponse; 
    }

}
