package com.examen.ef.dto.responses;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembreResponsesDto {
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateDeNaissance;
    private Boolean aVoter;
}
