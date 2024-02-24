package ru.donorsearch.backend.controller.dto.donation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class CountryDTO {
    private int id;
    private String title;
}

