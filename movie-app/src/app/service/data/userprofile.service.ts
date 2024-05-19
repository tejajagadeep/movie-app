import {
  HttpBackend,
  HttpClient,
  HttpErrorResponse,
} from '@angular/common/http';
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
    return this.http.put<UserProfile>(
      `${API_URL}/private/userProfile/update/${username}`,
      userProfile
    );
    // .pipe(catchError(this.handleError))
  }
  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'Unknown error!';
    if (error.error instanceof ErrorEvent) {
      // Client-side or network error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Backend error
      console.log(error);
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(() => ({
      status: error.status,
      message: error.statusText,
      error: error.error,
    }));
  }
}
