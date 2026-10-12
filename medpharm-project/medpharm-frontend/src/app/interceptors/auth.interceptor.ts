import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

const API_URL = 'http://localhost:8080/api';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token = inject(AuthService).getToken();
  const esApi = req.url.startsWith(API_URL);
  const esLogin = req.url.includes('/auth/login');

  if (token && esApi && !esLogin) {
    req = req.clone({
      setHeaders: { Authorization: `Bearer ${token}` }
    });
  }
  return next(req);
};