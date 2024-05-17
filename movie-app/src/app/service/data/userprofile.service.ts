import { HttpBackend, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserProfile } from '../../model/UserProfile';
import { Observable, catchError, throwError } from 'rxjs';
import { API_URL } from '../../app.constants';

@Injectable({
  providedIn: 'root',
})
export class UserprofileService {
  private httpHandler: HttpClient;

  constructor(private http: HttpClient, private handler: HttpBackend) {
    this.httpHandler = new HttpClient(handler);
  }

  getUserProfile(username: string): Observable<UserProfile> {
    return this.http
      .get<UserProfile>(
        `${API_URL}/private/userProfile/getUserById/${username}`
      )
      .pipe(catchError(this.handleError));
  }

  saveUserProfile(userProfile: UserProfile): Observable<UserProfile> {
    return this.httpHandler
      .post<UserProfile>(`${API_URL}/public/userProfile/addUser`, userProfile)
      .pipe(catchError(this.handleError));
  }

  updateUserProfile(
    username: string,
    userProfile: UserProfile
  ): Observable<UserProfile> {
    return this.http
      .put<UserProfile>(
        `${API_URL}/private/userProfile/update/${username}`,
        userProfile
      )
      .pipe(catchError(this.handleError));
  }
  private handleError(error: any): Observable<never> {
    console.error('An error occurred:', error); // log to console instead
    return throwError(
      () => new Error('Something bad happened; please try again later.')
    );
  }
}
