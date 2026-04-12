package com.examen.ef.mappers;

import org.springframework.stereotype.Component;

import com.examen.ef.dto.requests.MembreRequestDto;
import com.examen.ef.dto.responses.MembreResponsesDto;
import com.examen.ef.entities.Membre;

@Component
public class MembreMapper {
    public Membre convertirRequestDtoEnMembre(MembreRequestDto membreRequestDto) {
        Membre membre = new Membre();
        membre.setNom(membreRequestDto.getNom());
        membre.setPrenom(membreRequestDto.getPrenom());
        membre.setDateDeNaissance(membreRequestDto.getDateDeNaissance());
        return membre;
    }

    public MembreResponsesDto convertirResponsesDtoEnMembre(Membre membre) {
        MembreResponsesDto membreResponsesDto = new MembreResponsesDto();
        membreResponsesDto.setId(membre.getId());
        membreResponsesDto.setNom(membre.getNom());
        membreResponsesDto.setPrenom(membre.getPrenom());
        membreResponsesDto.setDateDeNaissance(membre.getDateDeNaissance());
        membreResponsesDto.setAVoter(membre.getAVoter());
        return membreResponsesDto;
    }
}
