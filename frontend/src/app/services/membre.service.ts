import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MembreResponseDto } from '../models/membre.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MembreService {

  private readonly urlBase = 'http://localhost:8080/membres';

  constructor(private httpClient: HttpClient) { }

  // Méthode 1 : GET /membres
  recupererTousLesMembres(): Observable<MembreResponseDto[]> {
    return this.httpClient.get<MembreResponseDto[]>(this.urlBase);
  }

  // Méthode 2 : PUT /membres/{id}/voter
  enregistrerLeVote(id: number): Observable<MembreResponseDto> {
    return this.httpClient.put<MembreResponseDto>(`${this.urlBase}/${id}/voter`, null);
  }
}
