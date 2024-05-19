import {
  HttpBackend,
  HttpClient,
  HttpErrorResponse,
  HttpHandler,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthenticationRequest } from '../../model/AuthenticationRequest';
import { Observable, catchError, throwError } from 'rxjs';
import { AuthenticationResponse } from '../../model/AuthenticationResponse';
import { API_URL } from '../../app.constants';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private httpHandler: HttpClient;

  constructor(private http: HttpClient, private handler: HttpBackend) {
    this.httpHandler = new HttpClient(handler);
  }

  authenticate(
    auth: AuthenticationRequest
  ): Observable<AuthenticationResponse> {
    return this.httpHandler
      .post<AuthenticationResponse>(`${API_URL}/public/auth/authenticate`, auth)
      .pipe(catchError(this.handleError));
  }

  isUserLoggedIn(username: string): Observable<boolean> {
    return this.http
      .get<boolean>(`${API_URL}/public/auth/validate?username=${username}`)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'Unknown error!';
    if (error.error instanceof ErrorEvent) {
      // Client-side or network error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Backend error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(() => ({
      status: error.status,
      message: error.statusText,
      error: error.error,
    }));
  }
}
