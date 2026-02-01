import { Routes } from '@angular/router';
import { TeamBuilderComponent } from './components/team-builder/team-builder.component';
import { ChampionsListComponent } from './components/champions-list/champions-list.component';
import { CompositionsListComponent } from './components/compositions-list/compositions-list.component';
import { AddChampionComponent } from './components/add-champion/add-champion.component';

export const routes: Routes = [
  { path: '', component: TeamBuilderComponent },
  { path: 'champions', component: ChampionsListComponent },
  { path: 'compositions', component: CompositionsListComponent },
  { path: 'add-champion', component: AddChampionComponent },
  { path: '**', redirectTo: '' }
];
