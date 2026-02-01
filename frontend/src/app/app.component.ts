import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TeamBuilderComponent } from './components/team-builder/team-builder.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, TeamBuilderComponent],
  template: `
    <router-outlet></router-outlet>
  `,
  styles: []
})
export class AppComponent {
  title = 'League of Legends Team Builder';
}
