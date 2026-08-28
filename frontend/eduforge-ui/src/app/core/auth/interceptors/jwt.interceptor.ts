import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';

import { TokenService } from '../services/token.service';

export const jwtInterceptor: HttpInterceptorFn = (request, next) => {

    /**
     * Inject TokenService
     *
     * Used to fetch JWT Token
     * from Browser Local Storage.
     */
    const tokenService = inject(TokenService);

    // Login request should never carry an old JWT
    if (request.url.includes('/login')) {
        return next(request);
    }

    /**
     * Read JWT Token
     */
    const token = tokenService.getToken();

     /**
   * If Token exists
   * Clone request and attach Authorization Header.
   *
   * HttpRequest is immutable,
   * therefore clone() is required.
   */
  if (token) {

    request = request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });

  }

  return next(request);

};