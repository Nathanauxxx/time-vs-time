import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TeamComposition } from '../models/team-composition.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TeamCompositionService {
  private apiUrl = `${environment.apiUrl}/compositions`;

  constructor(private http: HttpClient) {}

  getAllCompositions(): Observable<TeamComposition[]> {
    return this.http.get<TeamComposition[]>(this.apiUrl);
  }

  getCompositionById(id: number): Observable<TeamComposition> {
    return this.http.get<TeamComposition>(`${this.apiUrl}/${id}`);
  }

  saveComposition(composition: TeamComposition): Observable<TeamComposition> {
    return this.http.post<TeamComposition>(this.apiUrl, composition);
  }

  deleteComposition(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
