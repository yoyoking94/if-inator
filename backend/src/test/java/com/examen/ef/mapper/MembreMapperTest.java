package com.examen.ef.mapper;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import com.examen.ef.dto.requests.MembreRequestDto;
import com.examen.ef.dto.responses.MembreResponsesDto;
import com.examen.ef.entities.Membre;
import com.examen.ef.mappers.MembreMapper;

public class MembreMapperTest {
    private final MembreMapper membreMapper = new MembreMapper();

    @Test
    void convertirRequestDtoEnMembre_doitRetournerUnMembreAvecLesBonnesValeurs() {
        MembreRequestDto membreRequestDto = new MembreRequestDto();
        membreRequestDto.setNom("Dupond");
        membreRequestDto.setPrenom("Jean");
        membreRequestDto.setDateDeNaissance(LocalDate.of(1985, 3, 15));

        Membre membre = membreMapper.convertirRequestDtoEnMembre(membreRequestDto);

        assertThat(membre).isNotNull();
        assertThat(membre.getNom()).isEqualTo("Dupond");
        assertThat(membre.getPrenom()).isEqualTo("Jean");
        assertThat(membre.getDateDeNaissance()).isEqualTo(LocalDate.of(1985, 3, 15));
        assertThat(membre.getAVoter()).isEqualTo(false);
    }

    @Test
    void convertirMembreEnResponseDto_doitRetournerUnMembreAvecLesBonnesValeurs() {
        Membre membre = new Membre();
        membre.setId(1L);
        membre.setNom("Dupond");
        membre.setPrenom("Jean");
        membre.setDateDeNaissance(LocalDate.of(1985, 3, 15));
        membre.setAVoter(true);

        MembreResponsesDto membreResponsesDto = membreMapper.convertirResponsesDtoEnMembre(membre);
        assertThat(membreResponsesDto).isNotNull();
        assertThat(membreResponsesDto.getId()).isEqualTo(1L);
        assertThat(membreResponsesDto.getNom()).isEqualTo("Dupond");
        assertThat(membreResponsesDto.getPrenom()).isEqualTo("Jean");
        assertThat(membreResponsesDto.getDateDeNaissance()).isEqualTo(LocalDate.of(1985, 3, 15));
        assertThat(membreResponsesDto.getAVoter()).isEqualTo(true);
    }
}
