import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface RiotSyncResponse {
  success: boolean;
  message: string;
  total: number;
  champions?: any[];
}

@Injectable({
  providedIn: 'root'
})
export class RiotApiService {
  private apiUrl = `${environment.apiUrl}/riot`;

  constructor(private http: HttpClient) {}

  /**
   * Sincroniza todos os campeões da API da Riot Games
   */
  syncChampions(): Observable<RiotSyncResponse> {
    return this.http.post<RiotSyncResponse>(`${this.apiUrl}/sync-champions`, {});
  }

  /**
   * Obtém a versão atual da API Data Dragon
   */
  getApiVersion(): Observable<{ version: string; message: string }> {
    return this.http.get<{ version: string; message: string }>(`${this.apiUrl}/version`);
  }
}
