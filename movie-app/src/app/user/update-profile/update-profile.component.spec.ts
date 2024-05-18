import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateProfileComponent } from './update-profile.component';
import {
  HttpClientTestingModule,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import { UserprofileService } from '../../service/data/userprofile.service';
import { provideRouter } from '@angular/router';
import { AuthGuard } from '../../service/security/auth.guard';

describe('UpdateProfileComponent', () => {
  let component: UpdateProfileComponent;
  let fixture: ComponentFixture<UpdateProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdateProfileComponent, HttpClientTestingModule],
      providers: [
        provideHttpClientTesting(),
        UserprofileService,
        provideRouter([
          {
            path: 'update-profile',
            component: UpdateProfileComponent,
            canActivate: [AuthGuard],
          },
        ]),
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(UpdateProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
