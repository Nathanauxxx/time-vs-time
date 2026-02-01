import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Champion } from '../models/champion.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ChampionService {
  private apiUrl = `${environment.apiUrl}/champions`;

  constructor(private http: HttpClient) {}

  getAllChampions(): Observable<Champion[]> {
    return this.http.get<Champion[]>(this.apiUrl);
  }

  getChampionById(id: number): Observable<Champion> {
    return this.http.get<Champion>(`${this.apiUrl}/${id}`);
  }

  createChampion(champion: Champion): Observable<Champion> {
    return this.http.post<Champion>(this.apiUrl, champion);
  }

  updateChampion(id: number, champion: Champion): Observable<Champion> {
    return this.http.put<Champion>(`${this.apiUrl}/${id}`, champion);
  }

  deleteChampion(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
