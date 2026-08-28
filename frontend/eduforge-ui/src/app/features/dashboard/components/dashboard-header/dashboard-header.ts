// ============================================================
// EduForge - Dashboard Header
// ============================================================

import {
  Component,
  output
} from '@angular/core';


@Component({
  // Dashboard header selector.
  selector: 'app-dashboard-header',

  // No additional imports required.
  imports: [],

  // Dashboard header HTML.
  templateUrl: './dashboard-header.html',

  // Dashboard header styling.
  styleUrl: './dashboard-header.scss',
})
export class DashboardHeader {

  // ==========================================================
  // HEADER CONTENT
  // ==========================================================

  // Main dashboard heading.
  readonly title =
    'Super Admin Dashboard';

  // Dashboard description.
  readonly subtitle =
    'Manage your EduForge platform from one place.';


  // ==========================================================
  // OUTPUT EVENTS
  // ==========================================================

  /**
   * Emits when the Add School button is clicked.
   *
   * Dashboard receives this event and opens
   * the reusable AddSchoolModal.
   */
  readonly addSchool =
    output<void>();

}