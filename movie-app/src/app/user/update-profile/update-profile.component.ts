import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { ErrorMessage } from '../../model/ErrorMessage';
import { UserProfile } from '../../model/UserProfile';
import { UserprofileService } from '../../service/data/userprofile.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { TopBarComponent } from '../../navigation/top-bar/top-bar.component';
import { FooterComponent } from '../../navigation/footer/footer.component';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-update-profile',
  standalone: true,
  templateUrl: './update-profile.component.html',
  styleUrl: './update-profile.component.css',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    TopBarComponent,
    FooterComponent,
  ],
})
export class UpdateProfileComponent implements OnInit {
  updateForm!: FormGroup;
  errorMessage!: ErrorMessage;
  userprofile!: UserProfile;
  statusCode!: number;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private userprofileService: UserprofileService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    this.formBuilderGroup();
    this.getUserProfile();
    this.serviceError(this.statusCode);
  }

  updateUserProfile() {
    const username = sessionStorage.getItem('username') ?? '';
    if (this.updateForm.valid) {
      console.log(this.updateForm.value);
      this.userprofileService
        .updateUserProfile(username, this.updateForm.value)
        .subscribe({
          next: (v) => {
            console.log(v);
          },
          error: (e) => {
            console.error(e), (this.statusCode = e.status);
            this.errorMessage = e.error
            if (this.statusCode === 400 || this.statusCode === 401) {
              this.snackBar.open(
                'Wrong user credentials. Please Login again.',
                'Close',
                {
                  duration: 3000, // Duration in milliseconds
                }
              );
              console.error(e), sessionStorage.removeItem('token');
              sessionStorage.removeItem('username');
              this.router.navigate(['/login']);
            }
          },
          complete: () => {
            console.info('User Details saved successfully');
            this.router.navigate(['/profile']);
            this.snackBar.open('Profile has been Updated.', 'Close', {
              duration: 3000, // Duration in milliseconds
            });
          },
        });
    }
  }

  getUserProfile() {
    const username = sessionStorage.getItem('username') ?? '';
    this.userprofileService.getUserProfile(username).subscribe({
      next: (v) => {
        this.userprofile = v;
        this.updateForm.patchValue({
          firstName: v.firstName,
          lastName: v.lastName,
          email: v.email,
          phoneNumber: v.phoneNumber,
        });
      },
      error: (e) => {
        console.error(e), (this.statusCode = e.status);
        if (this.statusCode === 400 || this.statusCode === 401) {
          console.error(e), sessionStorage.removeItem('token');
          sessionStorage.removeItem('username');
          this.router.navigate(['/login']);
        }
      },
      complete: () => {
        console.info('user profile fetched successfully');
      },
    });
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
    this.updateForm.get('firstName')?.markAsTouched();
    this.updateForm.get('lastName')?.markAsTouched();
    this.updateForm.get('phoneNumber')?.markAsTouched();
    this.updateForm.get('email')?.markAsTouched();
  }
  formBuilderGroup() {
    this.updateForm = this.formBuilder.group({
      phoneNumber: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      email: ['', [Validators.required, this.emailValidator]],
    });
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

  emailValidator(control: AbstractControl): { [key: string]: boolean } | null {
    const email = control.value;
    const emailRegex = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.(com|in)$/i;
    const isValid = emailRegex.test(email);
    return isValid ? null : { invalidEmail: true };
  }

  get phoneNumber() {
    return this.updateForm.get('phoneNumber');
  }

  get firstName() {
    return this.updateForm.get('firstName');
  }
  get lastName() {
    return this.updateForm.get('lastName');
  }

  get email() {
    return this.updateForm.get('email');
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
}
