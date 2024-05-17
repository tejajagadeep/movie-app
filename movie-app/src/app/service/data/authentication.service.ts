import { HttpBackend, HttpClient, HttpHandler } from '@angular/common/http';
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

  private handleError(error: any): Observable<never> {
    console.error('An error occurred:', error); // log to console instead
    return throwError(
      () => new Error('Something bad happened; please try again later.')
    );
  }
}
