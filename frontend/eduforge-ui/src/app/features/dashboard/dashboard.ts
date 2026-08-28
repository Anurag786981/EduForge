// ============================================================
// EduForge - Dashboard
// ============================================================

import {
  Component,
  inject,
  OnInit,
  signal
} from '@angular/core';

import { CommonModule } from '@angular/common';

import {
  FormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import { HttpErrorResponse } from '@angular/common/http';

import { SchoolService } from '../../core/services/school';
import { UserService } from '../../core/services/user';

import { School } from '../../core/models/school';

import { DashboardHeader } from './components/dashboard-header/dashboard-header';
import { DashboardStatCards } from './components/dashboard-stat-cards/dashboard-stat-cards';
import { DataTable } from './components/data-table/data-table';
import { ActivityFeed } from './components/activity-feed/activity-feed';

import { AddSchoolModal } from '../schools/components/add-school-modal/add-school-modal';

import { DashboardStat } from './models/dashboard.model';


@Component({
  selector: 'app-dashboard',

  imports: [
    CommonModule,
    ReactiveFormsModule,
    DashboardHeader,
    DashboardStatCards,
    DataTable,
    ActivityFeed,
    AddSchoolModal
  ],

  templateUrl: './dashboard.html',

  styleUrl: './dashboard.scss',
})
export class Dashboard implements OnInit {

  // ==========================================================
  // SERVICES
  // ==========================================================

  private readonly schoolService =
    inject(SchoolService);

  private readonly userService =
    inject(UserService);

  private readonly fb =
    inject(FormBuilder);


  // ==========================================================
  // DASHBOARD DATA
  // ==========================================================

  readonly schools =
    signal<School[]>([]);

  readonly stats =
    signal<DashboardStat[]>([

      {
        title: 'Total Schools',
        value: 0,
        icon: 'school',
      },

      {
        title: 'Total Users',
        value: 0,
        icon: 'groups',
      },

      {
        title: 'Active Schools',
        value: 0,
        icon: 'verified',
      },

      {
        title: 'Overdue Billing',
        value: '—',
        placeholder: true,
        icon: 'receipt_long',
      },

    ]);


  // ==========================================================
  // ADD SCHOOL
  // ==========================================================

  readonly addSchoolModalOpen =
    signal(false);


  // ==========================================================
  // SCHOOL ACTION MODALS
  // ==========================================================

  readonly selectedSchool =
    signal<School | null>(null);

  readonly viewModalOpen =
    signal(false);

  readonly editModalOpen =
    signal(false);

  readonly confirmationModalOpen =
    signal(false);

  readonly pendingStatusSchool =
    signal<School | null>(null);

  readonly saving =
    signal(false);

  readonly errorMessage =
    signal('');

  readonly successMessage =
    signal('');


  // ==========================================================
  // EDIT SCHOOL FORM
  // ==========================================================

  readonly schoolForm =
    this.fb.nonNullable.group({

      schoolName: [
        '',
        [
          Validators.required,
          Validators.maxLength(100)
        ]
      ],

      diseCode: [
        '',
        [
          Validators.required,
          Validators.pattern(/^\d{11}$/)
        ]
      ],

      email: [
        '',
        [
          Validators.required,
          Validators.email,
          Validators.maxLength(100)
        ]
      ],

      phoneNumber: [
        '',
        [
          Validators.required,
          Validators.pattern(/^\d{10}$/),
          Validators.pattern(/^[6-9][0-9]{9}$/)
        ]
      ],

      address: [
        '',
        [
          Validators.required,
          Validators.maxLength(250)
        ]
      ]

    });


  // ==========================================================
  // INITIALIZATION
  // ==========================================================

  ngOnInit(): void {

    this.loadDashboardData();

  }


  // ==========================================================
  // LOAD DASHBOARD DATA
  // ==========================================================

  private loadDashboardData(): void {

    this.schoolService
      .getAllSchools()
      .subscribe({

        next: (schools) => {

          this.schools.set(schools);

          this.updateSchoolStats();

        },

        error: (error) => {

          console.error(
            'Failed to load schools for dashboard',
            error
          );

        }

      });


    this.userService
      .getAllUsers()
      .subscribe({

        next: (users) => {

          this.stats.update(
            currentStats => [

              currentStats[0],

              {
                ...currentStats[1],
                value: users.length,
              },

              currentStats[2],

              currentStats[3],

            ]
          );

        },

        error: (error) => {

          console.error(
            'Failed to load users for dashboard',
            error
          );

        }

      });

  }


  // ==========================================================
  // UPDATE SCHOOL STATISTICS
  // ==========================================================

  private updateSchoolStats(): void {

    this.stats.update(
      currentStats => [

        {
          ...currentStats[0],
          value: this.schools().length,
        },

        currentStats[1],

        {
          ...currentStats[2],
          value:
            this.schools()
              .filter(
                school => school.active
              )
              .length,
        },

        currentStats[3],

      ]
    );

  }


  // ==========================================================
  // ADD SCHOOL
  // ==========================================================

  openAddSchool(): void {

    this.addSchoolModalOpen.set(true);

  }


  closeAddSchoolModal(): void {

    this.addSchoolModalOpen.set(false);

  }


  onSchoolCreated(
    createdSchool: School
  ): void {

    this.addSchoolModalOpen.set(false);

    this.schools.update(
      schools => [
        ...schools,
        createdSchool
      ]
    );

    this.updateSchoolStats();

    this.successMessage.set(
      'School created successfully.'
    );

  }


  // ==========================================================
  // VIEW SCHOOL
  // ==========================================================

  onDashboardViewSchool(
    school: School
  ): void {

    this.selectedSchool.set(school);

    this.schoolService
      .setActiveSchool(school);

    this.viewModalOpen.set(true);

    // Get the latest backend data.
    this.schoolService
      .getSchoolById(school.id)
      .subscribe({

        next: (latestSchool) => {

          this.selectedSchool
            .set(latestSchool);

          this.schoolService
            .setActiveSchool(latestSchool);

        },

        error: (error) => {

          console.error(
            'Failed to load school details',
            error
          );

        }

      });

  }


  closeDashboardView(): void {

    this.viewModalOpen.set(false);

    this.selectedSchool.set(null);

  }


  // ==========================================================
  // EDIT SCHOOL
  // ==========================================================

  onDashboardEditSchool(
    school: School
  ): void {

    this.errorMessage.set('');

    this.selectedSchool.set(school);

    this.schoolForm.patchValue({

      schoolName:
        school.schoolName,

      diseCode:
        school.diseCode,

      email:
        school.email,

      phoneNumber:
        school.phoneNumber,

      address:
        school.address

    });

    this.editModalOpen.set(true);

  }


  closeDashboardEdit(): void {

    if (this.saving()) {
      return;
    }

    this.editModalOpen.set(false);

    this.schoolForm.reset();

    this.selectedSchool.set(null);

  }


  submitDashboardEdit(): void {

    if (this.schoolForm.invalid) {

      this.schoolForm.markAllAsTouched();

      return;

    }


    const school =
      this.selectedSchool();

    if (!school) {
      return;
    }


    this.saving.set(true);

    this.errorMessage.set('');


    this.schoolService
      .updateSchool(
        school.id,
        this.schoolForm.getRawValue()
      )
      .subscribe({

        next: (updatedSchool) => {

          this.schools.update(
            schools =>
              schools.map(
                item =>
                  item.id === updatedSchool.id
                    ? updatedSchool
                    : item
              )
          );

          this.schoolService
            .setActiveSchool(updatedSchool);

          this.editModalOpen.set(false);

          this.selectedSchool.set(null);

          this.schoolForm.reset();

          this.updateSchoolStats();

          this.successMessage.set(
            'School updated successfully.'
          );

          this.saving.set(false);

        },

        error: (
          error: HttpErrorResponse
        ) => {

          console.error(
            'Failed to update school',
            error
          );

          this.errorMessage.set(
            this.getApiErrorMessage(error)
          );

          this.saving.set(false);

        }

      });

  }


  // ==========================================================
  // ACTIVATE / DEACTIVATE
  // ==========================================================

  onDashboardStatusChange(
    school: School
  ): void {

    this.pendingStatusSchool.set(school);

    this.errorMessage.set('');

    this.confirmationModalOpen.set(true);

  }


  closeDashboardConfirmation(): void {

    if (this.saving()) {
      return;
    }

    this.confirmationModalOpen.set(false);

    this.pendingStatusSchool.set(null);

  }


  executeDashboardStatusChange(): void {

    const school =
      this.pendingStatusSchool();

    if (!school) {
      return;
    }


    this.saving.set(true);

    this.errorMessage.set('');


    const request$ =
      school.active

        ? this.schoolService
            .deactivateSchool(school.id)

        : this.schoolService
            .activateSchool(school.id);


    request$.subscribe({

      next: (updatedSchool) => {

        // Update the school directly in Dashboard.
        this.schools.update(
          schools =>
            schools.map(
              item =>
                item.id === updatedSchool.id
                  ? updatedSchool
                  : item
            )
        );


        this.schoolService
          .setActiveSchool(updatedSchool);


        this.confirmationModalOpen
          .set(false);

        this.pendingStatusSchool
          .set(null);


        this.updateSchoolStats();


        this.successMessage.set(
          school.active
            ? 'School deactivated successfully.'
            : 'School activated successfully.'
        );


        this.saving.set(false);

      },

      error: (
        error: HttpErrorResponse
      ) => {

        console.error(
          'Failed to change school status',
          error
        );

        this.errorMessage.set(
          this.getApiErrorMessage(error)
        );

        this.saving.set(false);

      }

    });

  }


  // ==========================================================
  // FORM VALIDATION
  // ==========================================================

  isFieldInvalid(
    fieldName:
      | 'schoolName'
      | 'diseCode'
      | 'email'
      | 'phoneNumber'
      | 'address'
  ): boolean {

    const control =
      this.schoolForm.get(
        fieldName
      );

    return !!control &&
      control.invalid &&
      (
        control.touched ||
        control.dirty
      );

  }


  // ==========================================================
  // API ERROR MESSAGE
  // ==========================================================

  private getApiErrorMessage(
    error: HttpErrorResponse
  ): string {

    if (error.error?.message) {

      return error.error.message;

    }


    if (error.error?.errors) {

      if (
        Array.isArray(
          error.error.errors
        )
      ) {

        const messages =
          error.error.errors
            .map(
              (item: any) =>
                item.message ||
                item.defaultMessage ||
                item.error
            )
            .filter(Boolean);

        if (messages.length > 0) {

          return messages.join(', ');

        }

      }


      if (
        typeof error.error.errors ===
        'object'
      ) {

        const messages =
          Object.values(
            error.error.errors
          )
            .filter(Boolean)
            .map(
              value => String(value)
            );

        if (messages.length > 0) {

          return messages.join(', ');

        }

      }

    }


    if (error.error?.detail) {

      return error.error.detail;

    }


    return (
      'Unable to process the school request. ' +
      'Please try again.'
    );

  }


  // ==========================================================
  // CLOSE NOTIFICATIONS
  // ==========================================================

  clearSuccess(): void {

    this.successMessage.set('');

  }


  clearError(): void {

    this.errorMessage.set('');

  }

}