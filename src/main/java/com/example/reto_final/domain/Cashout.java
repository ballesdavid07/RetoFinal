package com.example.reto_final.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("cashouts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cashout {
    @Id
    private Long id;

    @Column("user_id")
    @NotNull(message = "El identificador del usuario es requerido.")
    private Long userId;

    @Column("amount")
    @NotNull(message = "La cantidad no puede ser nula.")
    private Double amount;
}
