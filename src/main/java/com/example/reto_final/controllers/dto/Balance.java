package com.example.reto_final.controllers.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Balance {
    @NotNull(message = "El balance no puede ser nulo")
    @PositiveOrZero(message = "El balance debe ser mayor o igual a cero")
    private Double balance;
}
