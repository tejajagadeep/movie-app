import { CanActivateFn, Router } from '@angular/router';
import { Injectable, inject } from '@angular/core';

@Injectable()
class PermissionsService {
  canActivate(): boolean {
    if (localStorage.getItem('token') && localStorage.getItem('username')) {
      return true;
    }

    return false;
  }
}

// export const authGuard: CanActivateFn = (route, state) => {
//   return inject(PermissionsService).canActivate();
// };

export const AuthGuard: CanActivateFn = (route, state) => {
  // if (typeof window !== 'undefined' && window.localStorage) {
  let token = localStorage.getItem('token');
  let username = localStorage.getItem('username');
  const router = inject(Router);
  if (token !== null && username !== null) {
    return true;
  } else {
    router.navigate(['/login']);
    return false;
  }
  // } else {
  //   // Handle the case where localStorage is not available
  //   return false;
  // }
};

export const LoginGuard: CanActivateFn = (route, state) => {
  // if (typeof window !== 'undefined' && window.localStorage) {
  let token = localStorage.getItem('token');
  let username = localStorage.getItem('username');
  const router = inject(Router);
  if (token === null || username === null) {
    return true;
  } else {
    router.navigate(['/home']);
    return false;
  }
  // } else {
  //   // Handle the case where localStorage is not available
  //   return false;
  // }
};
