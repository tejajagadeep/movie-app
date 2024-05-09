import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthenticationService } from '../../service/data/authentication.service';
import { ErrorMessage } from '../../model/ErrorMessage';
import { AuthenticationRequest } from '../../model/AuthenticationRequest';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  statusCode!: number;
  errorMessage!: ErrorMessage;
  authent!: AuthenticationRequest;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthenticationService
  ) {}

  ngOnInit() {
    this.loginFormBuilder();
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
          localStorage.setItem('token', v.access_token);
        },
        error: (e) => {
          this.statusCode = e.status;
        },
        complete: () => {
          localStorage.setItem('username', this.loginForm.value.username);
          this.router.navigate(['/home']);
          console.info('User Details saved successfully');
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
}
