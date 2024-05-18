import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileComponent } from './profile.component';
import {
  HttpClientTestingModule,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import { UserprofileService } from '../../service/data/userprofile.service';
import { WishlistService } from '../../service/data/wishlist.service';
import { provideRouter } from '@angular/router';
import { AuthGuard } from '../../service/security/auth.guard';

describe('ProfileComponent', () => {
  let component: ProfileComponent;
  let fixture: ComponentFixture<ProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfileComponent, HttpClientTestingModule],
      providers: [
        provideHttpClientTesting(),
        UserprofileService,
        WishlistService,
        provideRouter([
          {
            path: 'profile',
            component: ProfileComponent,
            canActivate: [AuthGuard],
          },
        ]),
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
