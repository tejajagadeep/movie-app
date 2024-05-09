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

@Component({
  selector: 'app-update-profile',
  standalone: true,
  templateUrl: './update-profile.component.html',
  styleUrl: './update-profile.component.css',
  imports: [ReactiveFormsModule, CommonModule, TopBarComponent],
})
export class UpdateProfileComponent implements OnInit {
  signupForm!: FormGroup;
  errorMessage!: ErrorMessage;
  userprofile!: UserProfile;
  statusCode!: number;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private userprofileService: UserprofileService
  ) {}

  ngOnInit() {
    this.formBuilderGroup();
    this.getUserProfile();
  }

  updateUserProfile() {
    const username = localStorage.getItem('username') ?? '';
    if (this.signupForm.valid) {
      console.log(this.signupForm.value);
      this.userprofileService
        .updateUserProfile(username, this.signupForm.value)
        .subscribe({
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
            this.router.navigate(['/profile']);
          },
        });
    }
  }

  getUserProfile() {
    const username = localStorage.getItem('username') ?? '';
    this.userprofileService.getUserProfile(username).subscribe({
      next: (v) => {
        this.userprofile = v;
        this.signupForm.patchValue({
          firstName: v.firstName,
          lastName: v.lastName,
          email: v.email,
          phoneNumber: v.phoneNumber,
        });
      },
      error: (e) => console.error(e),
      complete: () => {
        console.info('user profile fetched successfully');
      },
    });
  }

  OnlyAlbhabets(event: any): boolean {
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
    this.signupForm.get('phoneNumber')?.markAsTouched();
    this.signupForm.get('email')?.markAsTouched();
  }
  formBuilderGroup() {
    this.signupForm = this.formBuilder.group({
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
    return this.signupForm.get('phoneNumber');
  }

  get firstName() {
    return this.signupForm.get('firstName');
  }
  get lastName() {
    return this.signupForm.get('lastName');
  }

  get email() {
    return this.signupForm.get('email');
  }
}
