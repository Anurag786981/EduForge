import { inject } from '@angular/core';

import {
  CanActivateChildFn,
  Router
} from '@angular/router';

import { AuthService } from '../services/auth.service';


/**
 * Protects child routes inside the authenticated application layout.
 *
 * This guard performs two checks:
 *
 * 1. Authentication check
 *    --------------------
 *    Makes sure a user is logged in.
 *
 *    If there is no logged-in user:
 *    → Redirect to /login
 *
 *
 * 2. Role authorization check
 *    ------------------------
 *    If a route defines allowed roles using:
 *
 *    data: {
 *      roles: ['SUPER_ADMIN']
 *    }
 *
 *    the logged-in user's role must be present
 *    in that list.
 *
 *    If the role is not allowed:
 *    → Redirect to /dashboard
 *
 *
 * This guard is used with:
 *
 * canActivateChild: [authGuard]
 *
 * in app.routes.ts.
 */
export const authGuard: CanActivateChildFn = (route) => {

  // ----------------------------------------------------------
  // Inject required services.
  // ----------------------------------------------------------

  // AuthService gives us the currently logged-in user.
  const authService = inject(AuthService);

  // Router is used to redirect the user.
  const router = inject(Router);


  // ----------------------------------------------------------
  // 1. AUTHENTICATION CHECK
  // ----------------------------------------------------------

  // Get the currently logged-in user from TokenService
  // through AuthService.
  const currentUser =
    authService.getCurrentUser();


  // If there is no logged-in user,
  // the user is not authenticated.
  //
  // Example:
  //
  // User directly enters:
  // http://localhost:4200/schools
  //
  // currentUser = null
  //
  // Therefore:
  // → Redirect to /login
  if (!currentUser) {

    return router.createUrlTree([
      '/login'
    ]);
  }


  // ----------------------------------------------------------
  // 2. ROLE AUTHORIZATION CHECK
  // ----------------------------------------------------------

  // Read the roles configured on the current route.
  //
  // Example from app.routes.ts:
  //
  // data: {
  //   roles: ['SUPER_ADMIN']
  // }
  //
  const allowedRoles =
    route.data?.['roles'] as
      string[] | undefined;


  // If the route does not define any roles,
  // every authenticated user can access it.
  //
  // Example:
  //
  // Dashboard may be available to:
  // SUPER_ADMIN
  // SCHOOL_ADMIN
  // TEACHER
  // etc.
  if (
    !allowedRoles ||
    allowedRoles.length === 0
  ) {

    return true;
  }


  // Get the role of the logged-in user.
  const userRole =
    currentUser.role;


  // ----------------------------------------------------------
  // 3. CHECK WHETHER USER ROLE IS ALLOWED
  // ----------------------------------------------------------

  // Example:
  //
  // Route allows:
  // ['SUPER_ADMIN']
  //
  // Logged-in user:
  // SUPER_ADMIN
  //
  // → Access granted.
  if (
    userRole &&
    allowedRoles.includes(userRole)
  ) {

    return true;
  }


  // ----------------------------------------------------------
  // 4. USER IS AUTHENTICATED BUT NOT AUTHORIZED
  // ----------------------------------------------------------

  // The user is logged in,
  // but their role is not allowed to access this route.
  //
  // Example:
  //
  // SCHOOL_ADMIN tries to open:
  // /schools
  //
  // /schools allows:
  // SUPER_ADMIN only
  //
  // Therefore:
  // → Redirect to dashboard.
  return router.createUrlTree([
    '/dashboard'
  ]);
};