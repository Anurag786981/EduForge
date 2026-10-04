import { Injectable, inject, signal } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs/operators';

export interface Breadcrumb {
  label: string;
  url: string;
}

/**
 * Builds the breadcrumb trail from the activated route tree.
 *
 * Convention: any route that should appear in the trail sets
 * `data: { breadcrumb: 'Schools' }`. Routes without that key are
 * skipped (useful for layout/shell routes that shouldn't render).
 *
 * Usage in route config:
 *   { path: 'schools', data: { breadcrumb: 'Schools' }, ... }
 *   { path: 'schools/add', data: { breadcrumb: 'Add School' }, ... }
 */
@Injectable({ providedIn: 'root' })
export class BreadcrumbService {
  private readonly router = inject(Router);
  private readonly activatedRoute = inject(ActivatedRoute);

  /** Reactive breadcrumb trail, always starts with Dashboard */
  readonly crumbs = signal<Breadcrumb[]>([{ label: 'Dashboard', url: '/dashboard' }]);

  constructor() {
    // Seed on initial load
    this.buildTrail();

    // Rebuild on every completed navigation
    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe(() => this.buildTrail());
  }

  private buildTrail(): void {
    const trail: Breadcrumb[] = [];
    let route: ActivatedRouteSnapshot | null = this.router.routerState.snapshot.root;
    let url = '';

    while (route) {
      const routePath = route.routeConfig?.path;
      if (routePath) {
        url += `/${routePath}`;
      }

      const label = route.data?.['breadcrumb'];
      if (label) {
        trail.push({ label, url });
      }

      route = route.firstChild;
    }

    this.crumbs.set(trail.length ? trail : [{ label: 'Dashboard', url: '/dashboard' }]);
  }
}
