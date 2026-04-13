import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MembreResponseDto } from '../../models/membre.model';
import { MembreService } from '../../services/membre.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-liste-membre',
  imports: [CommonModule],
  templateUrl: './liste-membre.html',
  styleUrl: './liste-membre.css',
})
export class ListeMembre implements OnInit {
  listeMembres: MembreResponseDto[] = [];
  messageErreur: string = '';

  constructor(
    private membreService: MembreService,
    private detecteurDeChangements: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.chargerTousLesMembre();
  }


  chargerTousLesMembre(): void {
    this.membreService.recupererTousLesMembres().subscribe({
      next: (membres) => {
        this.listeMembres = membres;
        this.detecteurDeChangements.detectChanges();
      },
      error: (erreur) => {
        this.messageErreur = erreur.error;
      }
    });
  }

  voterPourUnMembre(identifiant: number): void {
    this.membreService.enregistrerLeVote(identifiant).subscribe({
      next: (membreMisAJour) => {
        const index = this.listeMembres.findIndex(
          membre => membre.id === membreMisAJour.id
        );
        if (index !== -1) {
          this.listeMembres[index] = membreMisAJour;
          this.detecteurDeChangements.detectChanges();
        }
      },
      error: (erreur) => {
        this.messageErreur = erreur.error;
      }
    });
  }

}
