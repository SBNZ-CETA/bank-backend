package bank.service;

import facts.GeolocationResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GeoLocationService {
    private final WebClient webClient;

    public GeoLocationService(WebClient webClient) {
        this.webClient = webClient;
    }

    public GeolocationResponse getGeolocation(String ipAddress) {
        return webClient.get()
                .uri("http://api.ipstack.com/{ip}?access_key={apiKey}", ipAddress, "73ae2391744de53b8f113020d7a0c5bd")
                .retrieve()
                .bodyToMono(GeolocationResponse.class)
                .block();
    }
}
