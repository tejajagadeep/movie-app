import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TopBarComponent } from '../../navigation/top-bar/top-bar.component';
import { Top100MoviesComponent } from '../../movie/top100-movies/top100-movies.component';
import { FooterComponent } from '../../navigation/footer/footer.component';
@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  imports: [
    RouterModule,
    TopBarComponent,
    Top100MoviesComponent,
    FooterComponent,
  ],
})
export class HomeComponent {}
