import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Top100MoviesComponent } from './top100-movies.component';
import {
  HttpClientTestingModule,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import { WishlistService } from '../../service/data/wishlist.service';
import { MovieService } from '../../service/data/movie.service';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideRouter } from '@angular/router';
import { HomeComponent } from '../../public/home/home.component';
import { AuthGuard, LoginGuard } from '../../service/security/auth.guard';
import { UpdateProfileComponent } from '../../user/update-profile/update-profile.component';
import { ProfileComponent } from '../../user/profile/profile.component';
import { LoginComponent } from '../../public/login/login.component';

describe('Top100MoviesComponent', () => {
  let component: Top100MoviesComponent;
  let fixture: ComponentFixture<Top100MoviesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Top100MoviesComponent, HttpClientTestingModule],
      providers: [
        provideHttpClientTesting(),
        provideAnimations(),
        WishlistService,
        MovieService,
        provideRouter([
          {
            path: 'home',
            component: HomeComponent,
            canActivate: [AuthGuard],
          },
          {
            path: 'update-profile',
            component: UpdateProfileComponent,
            canActivate: [AuthGuard],
          },
          {
            path: 'profile',
            component: ProfileComponent,
            canActivate: [AuthGuard],
          },
          {
            path: 'wishlist',
            component: WishlistService,
            canActivate: [AuthGuard],
          },
          {
            path: 'login',
            component: LoginComponent,
            canActivate: [LoginGuard],
          },
        ]),
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(Top100MoviesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
