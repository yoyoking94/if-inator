package com.examen.ef.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.examen.ef.dto.responses.MembreResponsesDto;
import com.examen.ef.exceptions.GestionnaireDesExceptions;
import com.examen.ef.services.MembreService;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({ MembreController.class, GestionnaireDesExceptions.class })
public class MembreControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MembreService membreService;

    private MembreResponsesDto membreResponsesDto;

    @BeforeEach
    void initialiserLesDonneesDeTest() {
        membreResponsesDto = new MembreResponsesDto();
        membreResponsesDto.setId(1L);
        membreResponsesDto.setNom("Dupont");
        membreResponsesDto.setPrenom("Jean");
        membreResponsesDto.setDateDeNaissance(LocalDate.of(1985, 3, 15));
        membreResponsesDto.setAVoter(false);
    }

    @Test
    void recupererTousLesMembres_devraitRetournerStatut200AvecUneListe() throws Exception {
        when(membreService.recupererLesMembres()).thenReturn(List.of(membreResponsesDto));

        mockMvc.perform(get("/membres")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nom").value("Dupont"))
                .andExpect(jsonPath("$[0].prenom").value("Jean"))
                .andExpect(jsonPath("$[0].aVoter").value(false));
    }

    @Test
    void recupererTousLesMembres_devraitRetournerUneListeVide() throws Exception {
        when(membreService.recupererLesMembres()).thenReturn(List.of());

        mockMvc.perform(get("/membres")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void enregistrerLeVoteDuMembre_devraitRetournerStatut200AvecAVoteTrue() throws Exception {
        membreResponsesDto.setAVoter(true);
        when(membreService.enregistrerVoteDuMembre(1L)).thenReturn(membreResponsesDto);

        mockMvc.perform(put("/membres/1/voter")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.aVoter").value(true))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void enregistrerLeVoteDuMembre_devraitRetournerStatut500SiMembreDejaVote() throws Exception {
        when(membreService.enregistrerVoteDuMembre(1L))
                .thenThrow(new RuntimeException("Ce membre a déjà voté."));

        mockMvc.perform(put("/membres/1/voter")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Ce membre a déjà voté."));
    }
}
