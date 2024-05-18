import { CanActivateFn, Router } from '@angular/router';
import { Injectable, inject } from '@angular/core';

@Injectable()
class PermissionsService {
  canActivate(): boolean {
    if (sessionStorage.getItem('token') && sessionStorage.getItem('username')) {
      return true;
    }

    return false;
  }
}

// export const authGuard: CanActivateFn = (route, state) => {
//   return inject(PermissionsService).canActivate();
// };

export const AuthGuard: CanActivateFn = (route, state) => {
  // if (typeof window !== 'undefined' && window.sessionStorage) {
  const router = inject(Router);
  if (
    typeof window !== 'undefined' &&
    typeof sessionStorage !== 'undefined' &&
    sessionStorage
  ) {
    let token = sessionStorage.getItem('token');
    let username = sessionStorage.getItem('username');
    if (token !== null && username !== null) {
      return true;
    } else {
      router.navigate(['/login']);
      return false;
    }
  } else {
    router.navigate(['/login']);
    return false;
  }
};

export const LoginGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);

  if (
    typeof window !== 'undefined' &&
    typeof sessionStorage !== 'undefined' &&
    sessionStorage
  ) {
    let token = sessionStorage.getItem('token');
    let username = sessionStorage.getItem('username');
    if (token === null || username === null) {
      return true;
    } else {
      router.navigate(['/home']);
      return false;
    }
  } else {
    return false;
  }
};
