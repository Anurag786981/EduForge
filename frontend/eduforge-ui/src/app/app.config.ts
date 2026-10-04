import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { jwtInterceptor } from './core/auth/interceptors/jwt.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [provideZoneChangeDetection({ eventCoalescing: true }),
  provideRouter(routes), provideClientHydration(withEventReplay()),
  provideHttpClient(
    withFetch(),
    withInterceptors([
      jwtInterceptor
    ])
  )
  ]
   // Added withFetch() and withInterceptors() to enable fetch API support and JWT interceptor in HttpClient for better performance and modern web standards for SSR applications.
};


