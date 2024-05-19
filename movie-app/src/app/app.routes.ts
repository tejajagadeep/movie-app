import { Routes } from '@angular/router';
import { LoginComponent } from './public/login/login.component';
import { HomeComponent } from './public/home/home.component';
import { SignupComponent } from './public/signup/signup.component';
import { AuthGuard, LoginGuard } from './service/security/auth.guard';
import { ProfileComponent } from './user/profile/profile.component';
import { UpdateProfileComponent } from './user/update-profile/update-profile.component';
import { WishlistComponent } from './user/wishlist/wishlist.component';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
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
