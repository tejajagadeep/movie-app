import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import {
  HttpClientTestingModule,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import { AuthenticationService } from '../../service/data/authentication.service';
import { Router, provideRouter } from '@angular/router';
import { LoginGuard } from '../../service/security/auth.guard';
import { of, throwError } from 'rxjs';
import { provideAnimations } from '@angular/platform-browser/animations';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthenticationService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginComponent, HttpClientTestingModule],
      providers: [
        provideHttpClientTesting(),
        provideAnimations(),
        AuthenticationService,
        provideRouter([
          {
            path: 'login',
            component: LoginComponent,
            canActivate: [LoginGuard],
          },
        ]),
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthenticationService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should initialize the form with default values', () => {
    expect(component.loginForm).toBeDefined();
    expect(component.username?.value).toBe('');
    expect(component.password?.value).toBe('');
  });

  it('should invalidate the form when fields are empty', () => {
    component.loginForm.setValue({ username: '', password: '' });
    expect(component.loginForm.invalid).toBeTrue();
  });

  it('should invalidate the form when username is less than 5 characters', () => {
    component.loginForm.setValue({
      username: 'user',
      password: 'Password123!',
    });
    expect(component.loginForm.invalid).toBeTrue();
  });

  it('should invalidate the form when password is less than 8 characters', () => {
    component.loginForm.setValue({ username: 'username', password: 'Pass12!' });
    expect(component.loginForm.invalid).toBeTrue();
  });

  it('should invalidate the form when password does not meet complexity requirements', () => {
    component.loginForm.setValue({
      username: 'username',
      password: 'password',
    });
    expect(component.loginForm.invalid).toBeTrue();
  });

  it('should validate the form when username and password meet requirements', () => {
    component.loginForm.setValue({
      username: 'username',
      password: 'Password123!',
    });
    expect(component.loginForm.valid).toBeTrue();
  });

  it('should call authService.authenticate on login', () => {
    spyOn(authService, 'authenticate').and.returnValue(
      of({ access_token: 'token' })
    );
    spyOn(router, 'navigate');

    component.loginForm.setValue({
      username: 'username',
      password: 'Password123!',
    });
    component.login();

    expect(authService.authenticate).toHaveBeenCalledWith({
      username: 'username',
      password: 'Password123!',
    });
  });

  it('should handle authentication error', () => {
    spyOn(authService, 'authenticate').and.returnValue(
      throwError(() => ({ status: 401, statusText: 'Unauthorized' }))
    );

    component.loginForm.setValue({
      username: 'username',
      password: 'Password123!',
    });
    component.login();

    expect(component.statusCode).toBe(401);
  });

  it('should navigate to /home on successful login', () => {
    spyOn(authService, 'authenticate').and.returnValue(
      of({ access_token: 'token' })
    );
    spyOn(router, 'navigate');

    component.loginForm.setValue({
      username: 'username',
      password: 'Password123!',
    });
    component.login();

    expect(router.navigate).toHaveBeenCalledWith(['/home']);
  });
});
