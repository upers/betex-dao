package com.vareger.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PageablePayload<PI> {
    private List<PI> thisPageItems = new ArrayList<>();
    private Integer totalNumber;
    private Integer lastPageIndex;
}
