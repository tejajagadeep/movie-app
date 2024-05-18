import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SignupComponent } from './signup.component';
import {
  HttpClientTestingModule,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';
import { UserprofileService } from '../../service/data/userprofile.service';
import { LoginGuard } from '../../service/security/auth.guard';
import { of, throwError } from 'rxjs';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { ErrorMessage } from '../../model/ErrorMessage';

describe('SignupComponent', () => {
  let component: SignupComponent;
  let fixture: ComponentFixture<SignupComponent>;
  let userprofileService: jasmine.SpyObj<UserprofileService>;
  let snackBar: jasmine.SpyObj<MatSnackBar>;

  beforeEach(async () => {
    const userprofileServiceSpy = jasmine.createSpyObj('UserprofileService', [
      'saveUserProfile',
    ]);
    const snackBarSpy = jasmine.createSpyObj('MatSnackBar', ['open']);
    await TestBed.configureTestingModule({
      imports: [
        SignupComponent,
        HttpClientTestingModule,
        ReactiveFormsModule,
        MatSnackBarModule,
      ],
      providers: [
        provideHttpClientTesting(),
        { provide: UserprofileService, useValue: userprofileServiceSpy },
        { provide: MatSnackBar, useValue: snackBarSpy },
        provideRouter([
          {
            path: 'signup',
            component: SignupComponent,
            canActivate: [LoginGuard],
          },
        ]),
      ],
    }).compileComponents();
    userprofileService = TestBed.inject(
      UserprofileService
    ) as jasmine.SpyObj<UserprofileService>;
    snackBar = TestBed.inject(MatSnackBar) as jasmine.SpyObj<MatSnackBar>;

    fixture = TestBed.createComponent(SignupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize the form with default values', () => {
    expect(component.signupForm).toBeDefined();
    expect(component.username?.value).toBe('');
    expect(component.password?.value).toBe('');
    expect(component.confirmPassword?.value).toBe('');
    expect(component.phoneNumber?.value).toBe('');
    expect(component.firstName?.value).toBe('');
    expect(component.lastName?.value).toBe('');
    expect(component.dateOfBirth?.value).toBe('');
    expect(component.email?.value).toBe('');
  });

  it('should invalidate the form when fields are empty', () => {
    component.submitForm();
    expect(component.signupForm.invalid).toBeTrue();
  });

  it('should call saveUserProfile on form submission when form is valid', () => {
    component.signupForm.setValue({
      username: 'testuser',
      password: 'Password123!',
      confirmPassword: 'Password123!',
      phoneNumber: '1234567890',
      firstName: 'John',
      lastName: 'Doe',
      dateOfBirth: '2000-01-01',
      email: 'test@example.com',
    });

    userprofileService.saveUserProfile.and.returnValue(of());

    component.saveUserProfile();
    expect(userprofileService.saveUserProfile).toHaveBeenCalled();
  });

  it('should handle saveUserProfile error', () => {
    component.signupForm.setValue({
      username: 'testuser',
      password: 'Password123!',
      confirmPassword: 'Password123!',
      phoneNumber: '1234567890',
      firstName: 'John',
      lastName: 'Doe',
      dateOfBirth: '2000-01-01',
      email: 'test@example.com',
    });

    const errorResponse: ErrorMessage = {
      status: 500,
      message: 'Internal Server Error. Please try again later.',
      timeStamp: new Date(),
    };
    userprofileService.saveUserProfile.and.returnValue(
      throwError(() => errorResponse)
    );

    component.saveUserProfile();
    expect(userprofileService.saveUserProfile).toHaveBeenCalled();
    expect(component.statusCode).toBe(500);
    // expect(component.errorMessage).toEqual(errorResponse);
    // expect(snackBar.open).toHaveBeenCalledWith(
    //   'Internal Server Error. Please try again later.',
    //   'Close',
    //   { duration: 3000 }
    // );
  });

  it('should validate email format', () => {
    const emailControl = component.signupForm.get('email');

    emailControl?.setValue('invalidemail');
    expect(emailControl?.errors?.['invalidEmail']).toBeTrue();

    emailControl?.setValue('valid.email@example.com');
    expect(emailControl?.valid).toBeTrue();
  });

  it('should match password and confirmPassword', () => {
    const confirmPasswordControl = component.signupForm.get('confirmPassword');

    component.signupForm.get('password')?.setValue('Password123@');
    confirmPasswordControl?.setValue('DifferentPassword123!');
    expect(confirmPasswordControl?.errors?.['confirmedValidator']).toBe(
      'Passwords do not match.'
    );
  });
});
