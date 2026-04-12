package com.examen.ef.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.examen.ef.dto.responses.MembreResponsesDto;
import com.examen.ef.entities.Membre;
import com.examen.ef.mappers.MembreMapper;
import com.examen.ef.repositories.MembreRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MembreServiceTest {

    @Mock
    private MembreRepository membreRepository;

    @Mock
    private MembreMapper membreMapper;

    @InjectMocks
    private MembreService membreService;

    private Membre membreQuiNaPasVote;
    private Membre membreQuiADejaVote;
    private MembreResponsesDto membreDTOAttendu;

    @BeforeEach
    void initialiserLesDonneesDeTest() {
        membreQuiNaPasVote = new Membre();
        membreQuiNaPasVote.setId(1L);
        membreQuiNaPasVote.setNom("Dupont");
        membreQuiNaPasVote.setPrenom("Jean");
        membreQuiNaPasVote.setDateDeNaissance(LocalDate.of(1985, 3, 15));
        membreQuiNaPasVote.setAVoter(false);

        membreQuiADejaVote = new Membre();
        membreQuiADejaVote.setId(2L);
        membreQuiADejaVote.setNom("Martin");
        membreQuiADejaVote.setPrenom("Sophie");
        membreQuiADejaVote.setDateDeNaissance(LocalDate.of(1990, 7, 22));
        membreQuiADejaVote.setAVoter(true);

        membreDTOAttendu = new MembreResponsesDto();
        membreDTOAttendu.setId(1L);
        membreDTOAttendu.setNom("Dupont");
        membreDTOAttendu.setPrenom("Jean");
        membreDTOAttendu.setDateDeNaissance(LocalDate.of(1985, 3, 15));
        membreDTOAttendu.setAVoter(true);
    }

    @Test
    void recupererTousLesMembres_devraitRetournerUneListeDeMembreDTO() {
        when(membreRepository.findAll()).thenReturn(List.of(membreQuiNaPasVote));
        when(membreMapper.convertirResponsesDtoEnMembre(membreQuiNaPasVote)).thenReturn(membreDTOAttendu);

        List<MembreResponsesDto> resultat = membreService.recupererLesMembres();

        assertEquals(1, resultat.size());
        verify(membreRepository, times(1)).findAll();
    }

    @Test
    void recupererTousLesMembres_devraitRetournerUneListeVideSiAucunMembre() {
        when(membreRepository.findAll()).thenReturn(List.of());

        List<MembreResponsesDto> resultat = membreService.recupererLesMembres();

        assertTrue(resultat.isEmpty());
    }

    @Test
    void enregistrerLeVoteDuMembre_devraitPasserAVoteATrue() {
        when(membreRepository.findById(1L)).thenReturn(Optional.of(membreQuiNaPasVote));
        when(membreRepository.save(membreQuiNaPasVote)).thenReturn(membreQuiNaPasVote);
        when(membreMapper.convertirResponsesDtoEnMembre(membreQuiNaPasVote)).thenReturn(membreDTOAttendu);

        MembreResponsesDto resultat = membreService.enregistrerVoteDuMembre(1L);

        assertTrue(resultat.getAVoter());
        verify(membreRepository, times(1)).save(membreQuiNaPasVote);
    }

    @Test
    void enregistrerLeVoteDuMembre_devraitLeverUneExceptionSiMembreDejaVote() {
        when(membreRepository.findById(2L)).thenReturn(Optional.of(membreQuiADejaVote));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            membreService.enregistrerVoteDuMembre(2L);
        });

        assertEquals("Ce membre a déjà voté.", exception.getMessage());
        verify(membreRepository, never()).save(any());
    }

    @Test
    void enregistrerLeVoteDuMembre_devraitLeverUneExceptionSiMembreInexistant() {
        when(membreRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            membreService.enregistrerVoteDuMembre(999L);
        });

        assertEquals("Membre non trouvé avec l'identifiant : 999", exception.getMessage());
    }
}