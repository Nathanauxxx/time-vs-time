import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Champion } from '../../models/champion.model';
import { ChampionService } from '../../services/champion.service';

@Component({
  selector: 'app-add-champion',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './add-champion.component.html',
  styleUrls: ['./add-champion.component.scss']
})
export class AddChampionComponent {
  champion: Partial<Champion> = {
    name: '',
    championClass: '',
    physicalDamage: 5,
    magicDamage: 5,
    tankiness: 5,
    crowdControl: 5,
    iconUrl: '',
    lanes: 'all'
  };

  loading = false;
  error = '';

  constructor(
    private championService: ChampionService,
    private router: Router
  ) {}

  onSubmit() {
    this.loading = true;
    this.error = '';
    
    this.championService.createChampion(this.champion as Champion).subscribe({
      next: (data) => {
        alert('Campeão criado com sucesso!');
        this.router.navigate(['/champions']);
      },
      error: (err) => {
        this.error = 'Erro ao criar campeão: ' + (err.error?.message || err.message);
        this.loading = false;
        console.error(err);
      }
    });
  }
}
