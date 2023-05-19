package com.es.phoneshop.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuickOrderItemsDto {
    @Valid
    List<QuickOrderItemDto> items = new ArrayList<>();
}
