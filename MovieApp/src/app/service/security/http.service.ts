import {
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class HttpService implements HttpInterceptor {
  constructor() {}

  intercept(request: HttpRequest<any>, next: HttpHandler) {
    let basicAuthHeaderString = localStorage.getItem('authenticatedUser');
    let token = 'Bearer ' + localStorage.getItem('token');

    if (basicAuthHeaderString && token) {
      request = request.clone({
        setHeaders: {
          Authorization: token,
        },
      });
    }
    return next.handle(request);
  }
}
