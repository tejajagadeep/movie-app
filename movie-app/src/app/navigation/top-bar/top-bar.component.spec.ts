import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopBarComponent } from './top-bar.component';
import { provideRouter } from '@angular/router';
import { HomeComponent } from '../../public/home/home.component';
import { AuthGuard, LoginGuard } from '../../service/security/auth.guard';
import { UpdateProfileComponent } from '../../user/update-profile/update-profile.component';
import { ProfileComponent } from '../../user/profile/profile.component';
import { WishlistService } from '../../service/data/wishlist.service';
import { LoginComponent } from '../../public/login/login.component';
import { SignupComponent } from '../../public/signup/signup.component';

describe('TopBarComponent', () => {
  let component: TopBarComponent;
  let fixture: ComponentFixture<TopBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TopBarComponent],
      providers: [
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
          {
            path: 'signup',
            component: SignupComponent,
            canActivate: [LoginGuard],
          },
        ]),
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(TopBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
