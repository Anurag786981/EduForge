import {
  Component,
  HostListener,
  inject,
  OnInit,
  signal
} from '@angular/core';

import { School } from '../../models/school';
import { SchoolService } from '../../services/school';
import { AuthService } from '../../auth/services/auth.service';

@Component({
  selector: 'app-school-switcher',
  standalone: true,
  imports: [],
  templateUrl: './school-switcher.html',
  styleUrl: './school-switcher.scss'
})
export class SchoolSwitcherComponent implements OnInit {

  private readonly schoolService = inject(SchoolService);
  private readonly authService = inject(AuthService);

  /* =========================================================
     SCHOOL STATE
     ========================================================= */

  schools = signal<School[]>([]);

  selectedSchool = signal<School | null>(null);

  dropdownOpen = signal(false);


  /* =========================================================
     INITIALIZATION
     ========================================================= */

  ngOnInit(): void {

    const currentUser = this.authService.getCurrentUser();

    const role = currentUser?.role;

    /*
     * SUPER ADMIN
     * Can switch between all schools.
     */
    if (role === 'SUPER_ADMIN') {

      this.schoolService.getAllSchools().subscribe({
        next: (schools) => {

          this.schools.set(schools);

          if (schools.length > 0) {
            this.selectedSchool.set(schools[0]);

            /*
             * Push the default active school into the
             * shared service so the rest of the app
             * (student lists, attendance, reports, etc.)
             * knows which school is in context.
             */
            this.schoolService.setActiveSchool(schools[0]);
          }

        },

        error: (error) => {
          console.error('Failed to load schools', error);
        }
      });

    }

    /*
     * SCHOOL ADMIN / OTHER SCHOOL USER
     * Only their assigned school is available.
     */
    else {

      this.schoolService.getMySchool().subscribe({
        next: (school) => {

          this.schools.set([school]);

          this.selectedSchool.set(school);

          this.schoolService.setActiveSchool(school);

        },

        error: (error) => {
          console.error('Failed to load my school', error);
        }
      });

    }
  }


  /* =========================================================
     DROPDOWN
     ========================================================= */

  toggleDropdown(): void {

    /*
     * Don't open dropdown when there is only
     * one school available.
     */
    if (this.schools().length <= 1) {
      return;
    }

    this.dropdownOpen.update(
      (open) => !open
    );
  }


  /* =========================================================
     SELECT SCHOOL
     ========================================================= */

  selectSchool(school: School): void {

    this.selectedSchool.set(school);

    /*
     * Propagate the change app-wide so every screen scoped
     * to "the active school" (students, teachers, attendance,
     * reports, etc.) reacts to the switch.
     */
    this.schoolService.setActiveSchool(school);

    /*
     * Close immediately after selecting.
     */
    this.dropdownOpen.set(false);
  }


  /* =========================================================
     CLOSE WHEN CLICKING OUTSIDE
     ========================================================= */

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent): void {

    /*
     * If dropdown isn't open, nothing to do.
     */
    if (!this.dropdownOpen()) {
      return;
    }

    const target = event.target as HTMLElement;

    /*
     * Find the closest School Switcher element.
     *
     * Your HTML already has:
     *
     * <section class="school-switcher">
     *
     */
    const clickedInsideSwitcher =
      target.closest('.school-switcher');

    /*
     * Clicked outside the school switcher.
     * Close dropdown.
     */
    if (!clickedInsideSwitcher) {
      this.dropdownOpen.set(false);
    }
  }


  /* =========================================================
     ESC KEY
     ========================================================= */

  @HostListener('document:keydown.escape')
  onEscape(): void {

    if (this.dropdownOpen()) {
      this.dropdownOpen.set(false);
    }
  }

}