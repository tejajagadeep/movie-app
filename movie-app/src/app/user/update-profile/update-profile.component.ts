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
    private userprofileService: UserprofileService
  ) {}

  ngOnInit() {
    this.formBuilderGroup();
    this.getUserProfile();
  }

  updateUserProfile() {
    const username = localStorage.getItem('username') ?? '';
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
            if (this.statusCode === 400 || this.statusCode === 401) {
              console.error(e), localStorage.removeItem('token');
              localStorage.removeItem('username');
              this.router.navigate(['/login']);
            }
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
          console.error(e), localStorage.removeItem('token');
          localStorage.removeItem('username');
          this.router.navigate(['/login']);
        }
      },
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
}
