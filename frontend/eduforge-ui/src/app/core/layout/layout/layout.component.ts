import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MatSidenavModule } from '@angular/material/sidenav';

import { SidebarComponent } from '../sidebar/sidebar.component';
import { HeaderComponent } from '../header/header.component';
import { BreadcrumbComponent } from '../breadcrumb/breadcrumb.component';
import { ThemeService } from '../../theme/theme';

/**
 * Master application shell.
 *
 * Layout:
 *   ------------------------------------------
 *   | Sidebar | Header                       |
 *   |         |------------------------------|
 *   |         | Breadcrumb                   |
 *   |         |------------------------------|
 *   |         | Router Outlet                |
 *   ------------------------------------------
 *
 * Layout owns shared UI state:
 * - Sidebar collapsed/expanded
 * - Application theme
 */
@Component({
  selector: 'ef-layout',
  standalone: true,
  imports: [
    RouterOutlet,
    MatSidenavModule,
    SidebarComponent,
    HeaderComponent,
    BreadcrumbComponent,
  ],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LayoutComponent {

  private readonly themeService = inject(ThemeService);

  /** Single source of truth for sidebar collapse state */
  readonly sidebarCollapsed = signal<boolean>(false);

  protected onToggleSidebar(): void {
    this.sidebarCollapsed.update((collapsed) => !collapsed);
  }

  protected onToggleTheme(): void {
    this.themeService.toggle();
  }

  protected onLogout(): void {
    // Keep your existing logout implementation here later.
    // Example:
    // this.authService.logout();
    // this.router.navigate(['/login']);
  }
}