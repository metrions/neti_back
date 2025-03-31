package com.example.neti_back.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class QueryRequestDto {
    private UUID sessionId;

    private Integer placeNumber;
}
