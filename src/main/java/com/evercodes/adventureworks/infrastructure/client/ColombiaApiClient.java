package com.evercodes.adventureworks.infrastructure.client;

import com.evercodes.adventureworks.application.dto.ColombiaMapResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class ColombiaApiClient {

    private final RestClient restClient;

    public ColombiaApiClient(RestClient colombiaApiRestClient) {
        this.restClient = colombiaApiRestClient;
    }

    public List<ColombiaMapResponse> getMaps() {
        return restClient.get()
                .uri("/Map")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}