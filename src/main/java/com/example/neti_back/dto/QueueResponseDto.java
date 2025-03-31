package com.example.neti_back.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class QueueResponseDto {
    private List<Integer> places;
    private List<Integer> choosedPlaces;
}
