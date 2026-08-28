import {
  ChangeDetectionStrategy,
  Component,
  inject,
  output,
  signal
} from '@angular/core';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { MatBadgeModule } from '@angular/material/badge';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatDividerModule } from '@angular/material/divider';

import { ProfileMenuComponent } from '../profile-menu/profile-menu.component';
import { AuthService } from '../../auth/services/auth.service';


/**
 * Top application header/toolbar.
 *
 * The header displays the currently logged-in user.
 *
 * User information comes from AuthService so the header
 * and sidebar always show the same logged-in user.
 */
@Component({
  selector: 'ef-header',
  standalone: true,

  imports: [
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatMenuModule,
    MatBadgeModule,
    MatTooltipModule,
    MatDividerModule,
    ProfileMenuComponent,
  ],

  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',

  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HeaderComponent {

  /**
   * Authentication service provides the logged-in user.
   */
  private readonly authService = inject(AuthService);


  /**
   * Get the currently logged-in user.
   *
   * AuthService reads this information from TokenService.
   */
  protected readonly currentUser =
    this.authService.getCurrentUser();


  /**
   * Display name for the profile menu.
   */
  protected readonly userName =
    this.currentUser
      ? `${this.currentUser.firstName} ${this.currentUser.lastName}`.trim()
      : 'User';


  /**
   * Display-friendly role name for the profile menu.
   *
   * Backend role:
   * SUPER_ADMIN
   *
   * UI role:
   * Super Admin
   */
  protected readonly userRole =
    this.formatRole(this.currentUser?.role);


  /**
   * Notification count placeholder.
   *
   * We will connect this to the real notification API later.
   */
  protected readonly notificationCount = signal<number>(0);


  /**
   * Sidebar toggle event.
   */
  readonly toggleSidebar = output<void>();


  /**
   * Theme toggle event.
   */
  readonly toggleTheme = output<void>();


  /**
   * Logout event.
   */
  readonly logout = output<void>();


  /**
   * Local search box state.
   */
  readonly searchQuery = signal<string>('');


  /**
   * Handle global search input.
   */
  protected onSearchInput(event: Event): void {

    const value =
      (event.target as HTMLInputElement).value;

    this.searchQuery.set(value);
  }


  /**
   * Convert backend role name into display text.
   */
  private formatRole(role?: string): string {

    if (!role) {
      return 'User';
    }

    switch (role) {

      case 'SUPER_ADMIN':
        return 'Super Admin';

      case 'SCHOOL_ADMIN':
        return 'School Admin';

      default:
        return role
          .toLowerCase()
          .split('_')
          .map(word =>
            word.charAt(0).toUpperCase() + word.slice(1)
          )
          .join(' ');
    }
  }
}