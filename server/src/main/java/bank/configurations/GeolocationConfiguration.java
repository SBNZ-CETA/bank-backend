package bank.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GeolocationConfiguration {
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("http://api.ipstack.com/").build();
    }
}