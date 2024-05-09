import { Routes } from '@angular/router';
import { LoginComponent } from './public/login/login.component';
import { HomeComponent } from './public/home/home.component';
import { Top100MoviesComponent } from './movie/top100-movies/top100-movies.component';
import { SignupComponent } from './public/signup/signup.component';
import { AuthGuard, LoginGuard } from './service/security/auth.guard';
import { FavoriteListComponent } from './user/favorite-list/favorite-list.component';
import { ProfileComponent } from './user/profile/profile.component';
import { UpdateProfileComponent } from './user/update-profile/update-profile.component';
import { MovieDetailsComponent } from './movie/movie-details/movie-details.component';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent, canActivate: [LoginGuard] },
  {
    path: 'favorite',
    component: FavoriteListComponent,
    canActivate: [AuthGuard],
  },
  { path: 'signup', component: SignupComponent, canActivate: [LoginGuard] },
  {
    path: 'top-100-movies',
    component: Top100MoviesComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'movie-details/:id',
    component: MovieDetailsComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'update-profile',
    component: UpdateProfileComponent,
    canActivate: [AuthGuard],
  },
  { path: '**', redirectTo: 'home' },
];