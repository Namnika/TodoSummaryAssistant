package com.todoSummaryApplication.todoSummary.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TodoResponseDto {
    @NotNull
    private Long id;

    @NotNull
    private String todoItem;
}
