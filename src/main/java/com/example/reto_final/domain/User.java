package com.example.reto_final.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private Long id;

    @Column("name")
    @NotBlank(message = "El nombre es requerido")
    private String name;

    @Column("balance")
    @NotNull(message = "El balance es requerido")
    private Double balance;
}
