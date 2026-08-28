import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

import { LoginRequest, LoginResponse, LoginUser } from '../models/auth.model';
import { TokenService } from './token.service';
import { AuthConstants } from '../constants/auth.constants';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  /**
   * Angular Dependency Injection
   *
   * HttpClient
   * -----------
   * Used to communicate with Spring Boot APIs.
   *
   * TokenService
   * ------------
   * Responsible for storing JWT Token
   * and Logged-in User information.
   */
  private readonly http = inject(HttpClient);
  private readonly tokenService = inject(TokenService);

  /**
   * Login User
   *
   * Sends user credentials to Spring Boot.
   *
   * If login is successful:
   * 1. Save JWT Token
   * 2. Save Logged-in User
   *
   * Returns complete response back to component.
   *
   * @param request Login Request
   * @returns LoginResponse
   */
  login(request: LoginRequest): Observable<LoginResponse> {

    return this.http
      .post<LoginResponse>(AuthConstants.LOGIN_API, request)
      .pipe(

        tap((response) => {

          // Save JWT Token
          this.tokenService.saveToken(response.data.token);

          // Save Logged-in User
          this.tokenService.saveUser(response.data);

        })

      );

  }

  /**
   * Logout User
   *
   * Clears complete authentication information
   * from browser storage.
   */
  logout(): void {

    this.tokenService.clear();

  }

  /**
   * Get Logged-in User
   *
   * @returns LoginUser
   */
  getCurrentUser(): LoginUser | null {

    return this.tokenService.getUser();

  }

  /**
   * Get JWT Token
   *
   * Used by JWT Interceptor.
   *
   * @returns JWT Token
   */
  getToken(): string | null {

    return this.tokenService.getToken();

  }

  /**
   * Check Login Status
   *
   * @returns true if user is logged in
   */
  isLoggedIn(): boolean {

    return this.tokenService.isLoggedIn();

  }

}