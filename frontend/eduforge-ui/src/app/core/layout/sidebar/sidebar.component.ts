/**
 * ==========================================================
 * EduForge ERP
 * Enterprise Sidebar
 *
 * Responsibilities:
 * - Render navigation
 * - Display organization context
 * - Display current user
 * - Handle collapsed state
 * - Emit logout event
 *
 * Navigation items come from sidebar-nav.config.ts
 * and are filtered based on the logged-in user's role.
 * ==========================================================
 */

import {
  AfterViewInit,
  ChangeDetectionStrategy,
  Component,
  computed,
  ElementRef,
  inject,
  input,
  output,
  ViewChild
} from '@angular/core';

import {
  RouterLink,
  RouterLinkActive
} from '@angular/router';

import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTooltipModule } from '@angular/material/tooltip';

import { SchoolSwitcherComponent } from '../school-switcher/school-switcher';
import { AuthService } from '../../auth/services/auth.service';

import { SIDEBAR_NAV_ITEMS } from './sidebar-nav.config';
import { NavItem } from '../../shared/models/nav-item.model';


@Component({
  // Sidebar component selector.
  selector: 'ef-sidebar',

  // Keep the sidebar standalone.
  standalone: true,

  // Register components, directives and Angular Material modules.
  imports: [
    RouterLink,
    RouterLinkActive,
    MatIconModule,
    MatButtonModule,
    MatTooltipModule,
    SchoolSwitcherComponent
  ],

  // Sidebar HTML template.
  templateUrl: './sidebar.component.html',

  // Sidebar styles.
  styleUrl: './sidebar.component.scss',

  // Keep the existing OnPush change detection strategy.
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SidebarComponent implements AfterViewInit {

  /**
   * ----------------------------------------------------------
   * Sidebar State
   * ----------------------------------------------------------
   */

  // Receive the collapsed state from the parent layout.
  readonly collapsed = input(false);


  /**
   * ----------------------------------------------------------
   * Logout
   * ----------------------------------------------------------
   */

  // Emit logout event to the parent layout.
  readonly logout = output<void>();


  /**
   * ----------------------------------------------------------
   * Authentication
   * ----------------------------------------------------------
   */

  // Inject the existing authentication service.
  private readonly authService = inject(AuthService);

  // Get the currently logged-in user from the existing auth state.
  protected readonly currentUser =
    this.authService.getCurrentUser();


  /**
   * ----------------------------------------------------------
   * User / Role
   * ----------------------------------------------------------
   */

  // Read the role from the logged-in user.
  protected readonly userRole =
    this.currentUser?.role ?? '';

  // Check whether the logged-in user is a Super Admin.
  protected readonly isSuperAdmin =
    this.userRole === 'SUPER_ADMIN';

  // Check whether the logged-in user is a School Admin.
  protected readonly isSchoolAdmin =
    this.userRole === 'SCHOOL_ADMIN';


  /**
   * ----------------------------------------------------------
   * Navigation
   *
   * Navigation items come from the single configuration file.
   *
   * Items without allowedRoles:
   * - Available to all authenticated roles.
   *
   * Items with allowedRoles:
   * - Visible only when the current user's role is allowed.
   * ----------------------------------------------------------
   */

  protected readonly navItems: NavItem[] =
    SIDEBAR_NAV_ITEMS.filter(
      (item) =>
        !item.allowedRoles ||
        item.allowedRoles.includes(this.userRole)
    );


  /**
   * ----------------------------------------------------------
   * Nav Scroll Container
   *
   * Ensures the navigation list starts at the top when
   * the sidebar is initialized.
   * ----------------------------------------------------------
   */

  @ViewChild('navScroll')
  navScroll?: ElementRef<HTMLElement>;


  // Reset the navigation scroll position after the view loads.
  ngAfterViewInit(): void {

    if (this.navScroll) {
      this.navScroll.nativeElement.scrollTop = 0;
    }

  }


  /**
   * ----------------------------------------------------------
   * Academic Year
   * ----------------------------------------------------------
   */

  // Current academic year displayed by the sidebar.
  protected readonly academicYear = '2026-27';


  /**
   * ----------------------------------------------------------
   * User Initials
   * ----------------------------------------------------------
   */

  // Generate initials from the logged-in user's first name.
  protected readonly userInitials = computed(() => {

    // Read and clean the user's first name.
    const firstName =
      this.currentUser?.firstName?.trim() ?? '';

    // Use U when no first name is available.
    if (!firstName) {
      return 'U';
    }

    // Split the name into individual words.
    const names = firstName
      .split(/\s+/)
      .filter(Boolean);

    // Use the first character for a single-word name.
    if (names.length === 1) {
      return names[0].charAt(0).toUpperCase();
    }

    // Use the first and last word initials for multiple names.
    return (
      names[0].charAt(0) +
      names[names.length - 1].charAt(0)
    ).toUpperCase();

  });


  /**
   * ----------------------------------------------------------
   * Logout Handler
   * ----------------------------------------------------------
   */

  // Emit the logout event to the parent layout.
  protected onLogout(): void {
    this.logout.emit();
  }

}