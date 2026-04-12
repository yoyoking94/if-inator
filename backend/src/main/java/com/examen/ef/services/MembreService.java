package com.examen.ef.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examen.ef.dto.responses.MembreResponsesDto;
import com.examen.ef.entities.Membre;
import com.examen.ef.mappers.MembreMapper;
import com.examen.ef.repositories.MembreRepository;

@Service
public class MembreService {
    private final MembreMapper membreMapper;
    private final MembreRepository membreRepository;

    public MembreService(MembreMapper membreMapper, MembreRepository membreRepository) {
        this.membreMapper = membreMapper;
        this.membreRepository = membreRepository;
    };

    public List<MembreResponsesDto> recupererLesMembres() {
        return membreRepository.findAll()
                .stream()
                .map(membreMapper::convertirResponsesDtoEnMembre)
                .toList();
    }

    public MembreResponsesDto enregistrerVoteDuMembre(Long id) {
        Membre membre = membreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé avec l'identifiant : " + id));

        if (membre.getAVoter()) {
            throw new RuntimeException("Ce membre a déjà voté.");
        }

        membre.setAVoter(true);
        Membre membreSauvegarde = membreRepository.save(membre);
        return membreMapper.convertirResponsesDtoEnMembre(membreSauvegarde);
    }
}
