package com.evercodes.adventureworks.application.service;

import com.evercodes.adventureworks.application.commons.Result;
import com.evercodes.adventureworks.application.dto.ColombiaMapResponse;
import com.evercodes.adventureworks.application.enums.ResultType;
import com.evercodes.adventureworks.infrastructure.client.ColombiaApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClientException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ColombiaMapServiceTest {

    @Test
    void findAllShouldReturnSuccessWhenClientReturnsMaps() {
        ColombiaApiClient client = new ColombiaApiClient(null) {
            @Override
            public List<ColombiaMapResponse> getMaps() {
                return List.of(
                        new ColombiaMapResponse(1, "Map 1", "Description 1", null, List.of("https://image-1"), "https://source-1", null),
                        new ColombiaMapResponse(2, "Map 2", "Description 2", null, List.of("https://image-2"), "https://source-2", null)
                );
            }
        };

        ColombiaMapService service = new ColombiaMapService(client);

        Result<List<ColombiaMapResponse>> result = service.findAll();

        assertTrue(result.isSuccess());
        assertEquals(ResultType.Success, result.getType());
        assertEquals(2, result.getTotalRecords());
        assertEquals(2, result.getData().size());
    }

    @Test
    void findAllShouldReturnServiceUnavailableWhenClientFails() {
        ColombiaApiClient client = new ColombiaApiClient(null) {
            @Override
            public List<ColombiaMapResponse> getMaps() {
                throw new RestClientException("boom");
            }
        };

        ColombiaMapService service = new ColombiaMapService(client);

        Result<List<ColombiaMapResponse>> result = service.findAll();

        assertFalse(result.isSuccess());
        assertEquals(ResultType.ServiceUnavailable, result.getType());
        assertEquals("Unable to retrieve maps from external API", result.getMessage());
    }
}