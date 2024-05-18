import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Top100MoviesComponent } from '../../movie/top100-movies/top100-movies.component';
@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  imports: [RouterModule, Top100MoviesComponent],
})
export class HomeComponent {}
