package pl.maropce.etutor.domain.user;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class Main {
    public static void main(String[] args) {
        final WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();

        ResponseEntity<String> block = webClient.post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                            {
                                "username" : "admin",
                                "password" : "admin"
                            }
                        """)
                .retrieve()
                .toEntity(String.class).block();

        System.out.println(block.getStatusCode());
        System.out.println(block.getBody());

        System.out.println(block.getHeaders().getFirst(HttpHeaders.SET_COOKIE));



        ResponseEntity<String> block2 = webClient.get()
                .uri("/api/users")
                .header(HttpHeaders.COOKIE, block.getHeaders().getFirst(HttpHeaders.SET_COOKIE))
                .retrieve()
                .toEntity(String.class).block();

        System.out.println(block2.getStatusCode());
        System.out.println(block2.getBody());

    }
}
