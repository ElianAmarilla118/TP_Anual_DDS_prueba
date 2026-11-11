package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientMensajesConfig {
    //ver valor en pom.xml
    @Value("${conexiones.cola-de-mensajes:http://localhost:8085}")
    private String urlMensajes;

 @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    @Qualifier("restClientMensajes")
    public RestClient restClientMensajes(RestClient.Builder builder) {
        return builder
                .baseUrl(urlMensajes)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}