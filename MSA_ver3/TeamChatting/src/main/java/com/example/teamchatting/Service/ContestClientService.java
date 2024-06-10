package com.example.teamchatting.Service;
import com.example.teamchatting.dto.ContestDtoResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ContestClientService {
    private final WebClient webClient;

    public ContestClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://contest-service").build();
    }

    public Mono<ContestDtoResponse> getContestById(Integer id) {
        return webClient.get()
                .uri("/contests/{id}", id)
                .retrieve()
                .bodyToMono(ContestDtoResponse.class);
    }
}