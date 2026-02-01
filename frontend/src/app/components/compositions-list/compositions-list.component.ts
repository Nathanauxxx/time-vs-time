import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { TeamComposition } from '../../models/team-composition.model';
import { TeamCompositionService } from '../../services/team-composition.service';

@Component({
  selector: 'app-compositions-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './compositions-list.component.html',
  styleUrls: ['./compositions-list.component.scss']
})
export class CompositionsListComponent implements OnInit {
  compositions: TeamComposition[] = [];
  loading = true;
  error = '';

  constructor(private compositionService: TeamCompositionService) {}

  ngOnInit() {
    this.loadCompositions();
  }

  loadCompositions() {
    this.compositionService.getAllCompositions().subscribe({
      next: (data) => {
        this.compositions = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erro ao carregar composições';
        this.loading = false;
        console.error(err);
      }
    });
  }

  deleteComposition(id: number) {
    if (confirm('Tem certeza que deseja excluir esta composição?')) {
      this.compositionService.deleteComposition(id).subscribe({
        next: () => {
          this.compositions = this.compositions.filter(c => c.id !== id);
          alert('Composição excluída com sucesso!');
        },
        error: (err) => {
          alert('Erro ao excluir composição');
          console.error(err);
        }
      });
    }
  }

  formatDate(date: Date | string | undefined): string {
    if (!date) return 'N/A';
    return new Date(date).toLocaleDateString('pt-BR');
  }
}
