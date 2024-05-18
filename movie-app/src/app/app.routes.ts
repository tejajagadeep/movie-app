import { Routes } from '@angular/router';
import { LoginComponent } from './public/login/login.component';
import { HomeComponent } from './public/home/home.component';
import { Top100MoviesComponent } from './movie/top100-movies/top100-movies.component';
import { SignupComponent } from './public/signup/signup.component';
import { AuthGuard, LoginGuard } from './service/security/auth.guard';
import { ProfileComponent } from './user/profile/profile.component';
import { UpdateProfileComponent } from './user/update-profile/update-profile.component';
import { SomethingWentWrongComponent } from './errors/something-went-wrong/something-went-wrong.component';
import { WishlistComponent } from './user/wishlist/wishlist.component';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent, canActivate: [LoginGuard] },
  { path: 'wishlist', component: WishlistComponent, canActivate: [AuthGuard] },
  { path: 'signup', component: SignupComponent, canActivate: [LoginGuard] },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  {
    path: 'update-profile',
    component: UpdateProfileComponent,
    canActivate: [AuthGuard],
  },
  { path: '**', redirectTo: 'home' },
];
