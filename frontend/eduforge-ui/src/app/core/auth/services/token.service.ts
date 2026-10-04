import { Injectable } from '@angular/core';
import { LoginUser } from '../models/auth.model';
import { StorageKeys } from '../constants/auth.constants';


@Injectable({
  providedIn: 'root'
})
export class TokenService {

  /**
   * Constructor
   *
   * Currently no dependency is required.
   * Angular creates a singleton instance of this service.
   */
  constructor() { }

  /**
   * Save JWT Token
   *
   * This method stores the JWT token received after
   * successful login into browser Local Storage.
   *
   * Example:
   * eduforge_token = eyJhbGc...
   *
   * @param token JWT Token received from backend
   */
  saveToken(token: string): void {
    localStorage.setItem(StorageKeys.TOKEN_KEY, token);
  }

  /**
   * Get JWT Token
   *
   * Returns the stored JWT token.
   *
   * Used by:
   * - JWT Interceptor
   * - Auth Guard
   * - Auth Service
   *
   * @returns JWT Token or null
   */
  getToken(): string | null {
    return localStorage.getItem(StorageKeys.TOKEN_KEY);
  }

  /**
   * Remove JWT Token
   *
   * Called during Logout.
   */
  removeToken(): void {
    localStorage.removeItem(StorageKeys.TOKEN_KEY);
  }

  /**
   * Save Logged-in User Details
   *
   * Since Local Storage only stores String values,
   * object is converted into JSON String.
   *
   * Example:
   * {
   *   firstName:"Anurag",
   *   role:"SUPER_ADMIN"
   * }
   *
   * becomes
   *
   * '{"firstName":"Anurag","role":"SUPER_ADMIN"}'
   *
   * @param user Logged-in User
   */
  saveUser(user: LoginUser): void {

    localStorage.setItem(
      StorageKeys.USER_KEY,
      JSON.stringify(user)
    );

  }

  /**
   * Get Logged-in User
   *
   * Reads JSON String from Local Storage and
   * converts it back into LoginUser Object.
   *
   * @returns Logged-in User or null
   */
  getUser(): LoginUser | null {

    const user = localStorage.getItem(StorageKeys.USER_KEY);

    return user
      ? JSON.parse(user)
      : null;

  }

  /**
   * Remove Logged-in User
   *
   * Called during Logout.
   */
  removeUser(): void {
    localStorage.removeItem(StorageKeys.USER_KEY);
  }

  /**
   * Clear Authentication Data
   *
   * Removes both:
   * 1. JWT Token
   * 2. Logged-in User
   *
   * Used while:
   * - Logout
   * - Session Expired
   * - Token Invalid
   */
  clear(): void {

    this.removeToken();
    this.removeUser();

  }

  /**
   * Check User Login Status
   *
   * If Token exists in Local Storage
   * user is considered Logged In.
   *
   * NOTE:
   * Actual validation is performed by backend.
   *
   * @returns true if token exists
   */
  isLoggedIn(): boolean {

    return !!this.getToken();

  }

}