import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { UserprofileService } from '../../service/data/userprofile.service';
import { UserProfile } from '../../model/UserProfile';
import { ErrorMessage } from '../../model/ErrorMessage';
import { HttpStatus } from '../../model/HttpStatus';
import { Router } from '@angular/router';
import { TopBarComponent } from '../../navigation/top-bar/top-bar.component';
import { FooterComponent } from '../../navigation/footer/footer.component';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-signup',
  standalone: true,
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    TopBarComponent,
    FooterComponent,
  ],
})
export class SignupComponent implements OnInit {
  signupForm!: FormGroup;
  errorMessage!: ErrorMessage;
  userprofile!: UserProfile;
  HttpStatus = HttpStatus;
  statusCode!: number;
  currentDate: string = new Date().toISOString().split('T')[0];

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private userprofileService: UserprofileService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    this.formBuilderGroup();
    this.serviceError(this.statusCode);
  }

  saveUserProfile() {
    if (this.signupForm.valid) {
      this.userprofileService.saveUserProfile(this.signupForm.value).subscribe({
        next: (v) => {
          console.log(v);
        },
        error: (e) => {
          console.log('error');
          this.errorMessage = e.error;
          this.statusCode = e.status;
          console.error(e);
        },
        complete: () => {
          this.statusCode = 201;
          console.info('User Registered successfully');
          this.router.navigate(['/login']);
          this.snackBar.open('You have Successfully registered.', 'Close', {
            duration: 3000, // Duration in milliseconds
          });
        },
      });
    }
  }

  OnlyAlphabets(event: any): boolean {
    const charCode = event.which ? event.which : event.keyCode;

    if (
      charCode == 32 ||
      (charCode > 64 && charCode < 91) ||
      (charCode > 96 && charCode < 123)
    ) {
      return true;
    }

    return false;
  }
  OnlyNumbers(event: any): boolean {
    const charCode = event.which ? event.which : event.keyCode;

    if (charCode >= 48 && charCode <= 57) {
      return true;
    }

    return false;
  }

  submitForm() {
    this.signupForm.get('firstName')?.markAsTouched();
    this.signupForm.get('lastName')?.markAsTouched();
    this.signupForm.get('dateOfBirth')?.markAsTouched();
    this.signupForm.get('phoneNumber')?.markAsTouched();
    this.signupForm.get('username')?.markAsTouched();
    this.signupForm.get('email')?.markAsTouched();
    this.signupForm.get('password')?.markAsTouched();
    this.signupForm.get('confirmPassword')?.markAsTouched();
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

  serviceError(code: number) {
    if (this.statusCode === 503) {
      this.snackBar.open(
        'The service is currently unavailable. Please try again later.',
        'Close',
        {
          duration: 3000,
        }
      );
    } else if (this.statusCode === 500) {
      this.snackBar.open(
        'Internal Server Error. Please try again later.',
        'Close',
        {
          duration: 3000,
        }
      );
    } else if (this.statusCode === 404) {
      this.snackBar.open(
        'Not Found. Please try again later.',
        'Close',
        {
          duration: 3000,
        }
      );
    }
  }

  alreadyExists(errors: ErrorMessage, status: number) {
    console.log(errors.toString());
    if (status === 409) {
      this.snackBar.open(errors.message, 'Close', {
        duration: 3000,
      });
    }
  }
}
