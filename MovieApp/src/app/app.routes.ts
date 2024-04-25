import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/SignupComponent';
import { HomeComponent } from './home/home.component';
import { Top100MoviesComponent } from './movie/top100-movies/top100-movies.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'top-100-movies', component: Top100MoviesComponent },
  { path: '**', component: LoginComponent },
];
