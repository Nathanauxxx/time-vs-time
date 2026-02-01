import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Champion } from '../../models/champion.model';
import { ChampionService } from '../../services/champion.service';

@Component({
  selector: 'app-champions-list',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './champions-list.component.html',
  styleUrls: ['./champions-list.component.scss']
})
export class ChampionsListComponent implements OnInit {
  champions: Champion[] = [];
  filteredChampions: Champion[] = [];
  searchTerm: string = '';
  loading = true;
  error = '';

  constructor(private championService: ChampionService) {}

  ngOnInit() {
    this.loadChampions();
  }

  loadChampions() {
    this.championService.getAllChampions().subscribe({
      next: (data) => {
        this.champions = data;
        this.filteredChampions = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erro ao carregar campeões';
        this.loading = false;
        console.error(err);
      }
    });
  }

  filterChampions() {
    const term = this.searchTerm.toLowerCase().trim();
    if (!term) {
      this.filteredChampions = this.champions;
    } else {
      this.filteredChampions = this.champions.filter(champion =>
        champion.name.toLowerCase().includes(term) ||
        champion.championClass.toLowerCase().includes(term)
      );
    }
  }

  deleteChampion(id: number) {
    if (confirm('Tem certeza que deseja excluir este campeão?')) {
      this.championService.deleteChampion(id).subscribe({
        next: () => {
          this.champions = this.champions.filter(c => c.id !== id);
          this.filterChampions();
          alert('Campeão excluído com sucesso!');
        },
        error: (err) => {
          alert('Erro ao excluir campeão');
          console.error(err);
        }
      });
    }
  }
}
