package com.evercodes.adventureworks.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ColombiaMapResponse {

    private Integer id;
    private String name;
    private String description;
    private Integer departmentId;
    private List<String> urlImages;
    private String urlSource;
    private Object department;
}