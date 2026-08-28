import { Routes } from '@angular/router';

import { authGuard } from './core/auth/guards/auth.guard';
export const routes: Routes = [

  // Login page does not require authentication.
  {
    path: 'login',
    loadComponent: () =>
      import('./features/auth/login/login.component')
        .then(c => c.LoginComponent)
  },


  // Application shell for authenticated users.
  {
    path: '',

    loadComponent: () =>
      import('./core/layout/layout/layout.component')
        .then(c => c.LayoutComponent),

    // Check authentication and role for every child route.
    canActivateChild: [authGuard],

    children: [

      // Redirect the application root to Dashboard.
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      },


      // Dashboard is available to authenticated users.
      {
        path: 'dashboard',
        loadComponent: () =>
          import('./features/dashboard/dashboard')
            .then(c => c.Dashboard)
      },


      // All-schools management is Super Admin only.
      {
        path: 'schools',
        loadComponent: () =>
          import('./features/schools/schools/schools')
            .then(c => c.Schools),

        // Only Super Admin can access all schools.
        data: {
          roles: ['SUPER_ADMIN']
        }
      }

    ]
  }

];