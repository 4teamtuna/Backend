package com.example.teamchatting.Service;
import com.example.teamchatting.dto.ContestDtoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ContestClientService {
    private final RestTemplate restTemplate;

    public ContestClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ContestDtoResponse getContestById(Integer id) {
        String contestServiceUrl = "http://contest-service/contests/" + id;
        ResponseEntity<ContestDtoResponse> response = restTemplate.getForEntity(contestServiceUrl, ContestDtoResponse.class);
        return response.getBody();
    }
}