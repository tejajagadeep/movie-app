import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Top100MoviesComponent } from '../../movie/top100-movies/top100-movies.component';
import { LandingComponent } from '../landing/landing.component';
@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  imports: [RouterModule, Top100MoviesComponent, LandingComponent],
})
export class HomeComponent implements OnInit {
  isLoggedIn = false;

  ngOnInit(): void {
    if (
      typeof window !== 'undefined' &&
      typeof sessionStorage !== 'undefined' &&
      sessionStorage
    ) {
      let token = sessionStorage.getItem('token');
      let username = sessionStorage.getItem('username');
      if (token !== null && username !== null) {
        this.isLoggedIn = true;
      } else {
        this.isLoggedIn = false;
      }
    } else {
      this.isLoggedIn = false;
    }
  }
}
