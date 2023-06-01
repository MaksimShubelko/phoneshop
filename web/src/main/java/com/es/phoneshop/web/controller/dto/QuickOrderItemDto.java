package com.es.phoneshop.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuickOrderItemDto {
    @Size(max = 30, message = "Shouldn't be longer than 15 chars")
    private String phoneModel;
    @Min(value = 1, message = "Should be positive")
    private Long quantity;

    boolean hasErrors = true;
}
