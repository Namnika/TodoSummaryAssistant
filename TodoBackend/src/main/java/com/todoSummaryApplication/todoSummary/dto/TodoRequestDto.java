package com.todoSummaryApplication.todoSummary.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TodoRequestDto {
    @NotBlank(message = "Todo Item can not be Empty")
    private String todoItem;
}
