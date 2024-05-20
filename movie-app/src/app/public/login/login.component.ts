import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthenticationService } from '../../service/data/authentication.service';
import { ErrorMessage } from '../../model/ErrorMessage';
import { AuthenticationRequest } from '../../model/AuthenticationRequest';
import { TopBarComponent } from '../../navigation/top-bar/top-bar.component';
import { FooterComponent } from '../../navigation/footer/footer.component';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    RouterModule,
    TopBarComponent,
    FooterComponent,
  ],
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  statusCode!: number;
  errorMessage!: ErrorMessage;
  authent!: AuthenticationRequest;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthenticationService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    this.loginFormBuilder();
    this.serviceError(this.statusCode);
  }

  loginFormBuilder() {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(5)]],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.pattern(
            '^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}'
          ),
        ],
      ],
    });
  }

  login() {
    if (this.loginForm.valid) {
      this.authService.authenticate(this.loginForm.value).subscribe({
        next: (v) => {
          console.log(v);
          sessionStorage.setItem('token', v.access_token);
        },
        error: (e) => {
          this.statusCode = e.status;
        },
        complete: () => {
          this.statusCode = 201;
          sessionStorage.setItem('username', this.loginForm.value.username);
          this.router.navigate(['/home']);
          this.snackBar.open('You are Logged In', 'Close', {
            duration: 3000,
          });
          console.info('User logged in successfully');
        },
      });
    }
  }

  get username() {
    return this.loginForm.get('username');
  }
  get password() {
    return this.loginForm.get('password');
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
