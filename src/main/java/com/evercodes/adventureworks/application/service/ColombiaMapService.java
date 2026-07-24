package com.evercodes.adventureworks.application.service;

import com.evercodes.adventureworks.application.commons.Result;
import com.evercodes.adventureworks.application.dto.ColombiaMapResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
public class ColombiaMapService {

    private static final String MAP_PATH = "/Map";

    private final String baseUrl;

    public ColombiaMapService(@Value("${api.colombia.base-url}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Result<List<ColombiaMapResponse>> findAll() {
        try {
            RestClient restClient = RestClient.builder()
                    .baseUrl(baseUrl)
                    .build();

            List<ColombiaMapResponse> maps = restClient.get()
                    .uri(MAP_PATH)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

            if (maps == null) {
                return Result.ServiceUnavailable("The external maps API returned no data");
            }

            return Result.Success(maps, maps.size());
        } catch (RestClientException ex) {
            return Result.ServiceUnavailable("Unable to retrieve maps from external API: " + ex.getMessage());
        } catch (RuntimeException ex) {
            return Result.ServiceUnavailable("Unexpected error while retrieving maps: " + ex.getMessage());
        }
    }
}