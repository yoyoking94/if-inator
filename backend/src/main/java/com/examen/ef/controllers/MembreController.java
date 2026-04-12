package com.examen.ef.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examen.ef.dto.responses.MembreResponsesDto;
import com.examen.ef.services.MembreService;

@RestController
@RequestMapping("/membres")
public class MembreController {
    private final MembreService membreService;

    public MembreController(MembreService membreService) {
        this.membreService = membreService;
    }

    @GetMapping
    public ResponseEntity<List<MembreResponsesDto>> recupererTousLesMembres() {
        List<MembreResponsesDto> listeMembres = membreService.recupererLesMembres();
        return ResponseEntity.ok(listeMembres);
    }

    @PutMapping("/{id}/voter")
    public ResponseEntity<MembreResponsesDto> enregistrerLeVoteDuMembre(@PathVariable Long id) {
        MembreResponsesDto membreResponsesDto = membreService.enregistrerVoteDuMembre(id);
        return ResponseEntity.ok(membreResponsesDto);
    }
}
