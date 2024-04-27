import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  AsyncValidatorFn,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { UserprofileService } from '../service/data/userprofile.service';
import { UserProfile } from '../model/UserProfile';
import { ErrorMessage } from '../model/ErrorMessage';
import { Observable, map, switchMap, timer } from 'rxjs';
import { HttpStatus } from '../model/HttpStatus';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css',
})
export class SignupComponent implements OnInit {
  signupForm!: FormGroup;
  errorMessage!: ErrorMessage;
  userprofile!: UserProfile;
  HttpStatus = HttpStatus;
  statusCode!: number;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private userprofileService: UserprofileService
  ) {}

  ngOnInit() {
    this.formBuilderGroup();
  }

  saveUserProfile() {
    if (this.signupForm.valid) {
      console.log(this.signupForm.value);
      this.userprofileService.saveUserProfile(this.signupForm.value).subscribe({
        next: (v) => {
          console.log(v);
        },
        error: (e) => {
          this.errorMessage = e.error;
          this.statusCode = e.status;
          console.error(this.errorMessage.status);
        },
        complete: () => {
          console.info('User Details saved successfully');
          this.router.navigate(['/login']);
        },
      });
    }
  }

  formBuilderGroup() {
    this.signupForm = this.formBuilder.group(
      {
        username: ['', [Validators.required, Validators.minLength(5)]],
        password: ['', [Validators.required, this.passwordValidator]],
        confirmPassword: ['', [Validators.required]],
        phoneNumber: [
          '',
          [Validators.required, Validators.pattern(/^\d{10}$/)],
        ],
        firstName: ['', [Validators.required]],
        lastName: ['', [Validators.required]],
        dateOfBirth: ['', [Validators.required]],
        email: ['', [Validators.required, this.emailValidator]],
      },
      { validators: this.matchValidator('password', 'confirmPassword') }
    );
  }

  getErrorMessage(errors: ValidationErrors): string {
    if (errors?.['required']) {
      return 'Email is required.';
    } else if (errors?.['invalidEmail']) {
      return 'Invalid email format.';
    } else {
      return '';
    }
  }

  matchValidator(
    controlName: string,
    matchingControlName: string
  ): ValidatorFn {
    return (abstractControl: AbstractControl) => {
      const control = abstractControl.get(controlName);
      const matchingControl = abstractControl.get(matchingControlName);

      if (
        matchingControl!.errors &&
        !matchingControl!.errors?.['confirmedValidator']
      ) {
        return null;
      }

      if (control!.value !== matchingControl!.value) {
        const error = { confirmedValidator: 'Passwords do not match.' };
        matchingControl!.setErrors(error);
        return error;
      } else {
        matchingControl!.setErrors(null);
        return null;
      }
    };
  }

  lengthValidator(control: AbstractControl): ValidationErrors | null {
    const value = control.value;

    const errors: { [key: string]: boolean } = {};
    const minLength = value.toString().length < 5;
    const maxLength = value.toString().length > 10;
    if (!minLength) errors['minLength'] = true;
    if (!maxLength) errors['maxLength'] = true;

    return Object.keys(errors).length ? errors : null;
  }

  passwordValidator(
    control: AbstractControl
  ): { [key: string]: boolean } | null {
    const password = control.value;
    const hasNumber = /\d/.test(password);
    const hasUpper = /[A-Z]/.test(password);
    const hasLower = /[a-z]/.test(password);
    const hasSymbol = /\W/.test(password);
    const isLengthValid = password.length >= 8;
    const errors: { [key: string]: boolean } = {};
    if (!hasNumber) errors['number'] = true;
    if (!hasUpper) errors['upper'] = true;
    if (!hasLower) errors['lower'] = true;
    if (!hasSymbol) errors['symbol'] = true;
    if (!isLengthValid) errors['length'] = true;
    return Object.keys(errors).length ? errors : null;
  }

  emailValidator(control: AbstractControl): { [key: string]: boolean } | null {
    const email = control.value;
    const emailRegex = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.(com|in)$/i;
    const isValid = emailRegex.test(email);
    return isValid ? null : { invalidEmail: true };
  }

  get username() {
    return this.signupForm.get('username');
  }

  get phoneNumber() {
    return this.signupForm.get('phoneNumber');
  }

  get password() {
    return this.signupForm.get('password');
  }
  get confirmPassword() {
    return this.signupForm.get('confirmPassword');
  }
  get firstName() {
    return this.signupForm.get('firstName');
  }
  get lastName() {
    return this.signupForm.get('lastName');
  }

  get dateOfBirth() {
    return this.signupForm.get('dateOfBirth');
  }
  get email() {
    return this.signupForm.get('email');
  }
}
