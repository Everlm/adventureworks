package com.evercodes.adventureworks.application.service;

import com.evercodes.adventureworks.application.commons.Result;
import com.evercodes.adventureworks.application.dto.ColombiaMapResponse;
import com.evercodes.adventureworks.infrastructure.client.ColombiaApiClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
public class ColombiaMapService {

    private final ColombiaApiClient client;

    public ColombiaMapService(ColombiaApiClient client) {
        this.client = client;
    }

    public Result<List<ColombiaMapResponse>> findAll() {
        try {
            List<ColombiaMapResponse> maps = client.getMaps();

            if (maps == null) {
                return Result.ServiceUnavailable("The external maps API returned no data");
            }

            return Result.Success(maps, maps.size());
        } catch (RestClientException ex) {
            return Result.ServiceUnavailable("Unable to retrieve maps from external API");
        }
    }
}