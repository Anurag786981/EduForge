// ============================================================
// EduForge - Schools
// ============================================================

// Angular component utilities.
import {
  Component,
  inject,
  OnInit,
  signal
} from '@angular/core';

// Angular common functionality.
import { CommonModule } from '@angular/common';

// Reusable Add School modal.
import { AddSchoolModal } from '../components/add-school-modal/add-school-modal';

// Reactive Forms.
import {
  FormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

// HTTP error type used to read backend validation messages.
import { HttpErrorResponse } from '@angular/common/http';

// School model.
import { School } from '../../../core/models/school';

// School API service and request model.
import {
  SchoolRequest,
  SchoolService
} from '../../../core/services/school';

// Authentication service.
import { AuthService } from '../../../core/auth/services/auth.service';


@Component({
  selector: 'app-schools',

  imports: [
    CommonModule,
    ReactiveFormsModule,
    AddSchoolModal
  ],

  templateUrl: './schools.html',

  styleUrl: './schools.scss'
})
export class Schools implements OnInit {

  // ==========================================================
  // SERVICES
  // ==========================================================

  private readonly schoolService =
    inject(SchoolService);

  private readonly authService =
    inject(AuthService);

  private readonly fb =
    inject(FormBuilder);


  // ==========================================================
  // PAGE STATE
  // ==========================================================

  // Schools visible to the current logged-in user.
  readonly schools =
    signal<School[]>([]);

  // Search text.
  readonly searchText =
    signal('');

  // Status filter.
  readonly statusFilter =
    signal<'ALL' | 'ACTIVE' | 'INACTIVE'>('ALL');

  // Initial page loading.
  readonly loading =
    signal(true);

  // Add/Edit/Activate/Deactivate processing state.
  readonly saving =
    signal(false);

  // API error message.
  readonly errorMessage =
    signal('');

  // Success message.
  readonly successMessage =
    signal('');


  // ==========================================================
  // ACTION / MODAL STATE
  // ==========================================================

  // School selected for viewing.
  readonly selectedSchool =
    signal<School | null>(null);

  // School whose action menu is open.
  readonly openActionSchoolId =
    signal<number | null>(null);

  // Edit modal state.
  readonly formModalOpen =
    signal(false);

  // Reusable Add School modal state.
  readonly addSchoolModalOpen =
    signal(false);

  // View modal state.
  readonly viewModalOpen =
    signal(false);

  // Activate/Deactivate confirmation modal state.
  readonly confirmationModalOpen =
    signal(false);

  // Edit mode.
  readonly editMode =
    signal(false);

  // School currently being edited.
  readonly editingSchool =
    signal<School | null>(null);

  // School waiting for status confirmation.
  readonly pendingStatusSchool =
    signal<School | null>(null);


  // ==========================================================
  // CURRENT USER / ROLE
  // ==========================================================

  protected readonly currentUser =
    this.authService.getCurrentUser();

  protected readonly userRole =
    this.currentUser?.role ?? '';

  protected readonly isSuperAdmin =
    this.userRole === 'SUPER_ADMIN';

  protected readonly isSchoolAdmin =
    this.userRole === 'SCHOOL_ADMIN';


  // ==========================================================
  // EDIT SCHOOL FORM
  // ==========================================================

  // This form is now used only for Edit School.
  // Add School has its own form inside AddSchoolModal.
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

          // DISE must contain exactly 11 digits.
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

          // Exactly 10 digits.
          Validators.pattern(/^\d{10}$/),

          // Indian mobile number must start with 6-9.
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

    this.loadSchools();

  }


  // ==========================================================
  // LOAD SCHOOLS
  // ==========================================================

  loadSchools(): void {

    this.loading.set(true);

    this.errorMessage.set('');


    // --------------------------------------------------------
    // SUPER ADMIN
    // --------------------------------------------------------

    if (this.isSuperAdmin) {

      this.schoolService
        .getAllSchools()
        .subscribe({

          next: (schools) => {

            this.schools.set(schools);

            // Set first school as initial active context.
            if (schools.length > 0) {

              this.schoolService
                .setActiveSchool(schools[0]);

            }

            this.loading.set(false);

          },

          error: (error) => {

            console.error(
              'Failed to load all schools',
              error
            );

            this.errorMessage.set(
              'Unable to load schools. Please try again.'
            );

            this.loading.set(false);

          }

        });

      return;
    }


    // --------------------------------------------------------
    // SCHOOL ADMIN / OTHER SCHOOL USER
    // --------------------------------------------------------

    this.schoolService
      .getMySchool()
      .subscribe({

        next: (school) => {

          // School Admin sees only their assigned school.
          this.schools.set([school]);

          this.schoolService
            .setActiveSchool(school);

          this.loading.set(false);

        },

        error: (error) => {

          console.error(
            'Failed to load logged-in user school',
            error
          );

          this.errorMessage.set(
            'Unable to load your school. Please try again.'
          );

          this.loading.set(false);

        }

      });

  }


  // ==========================================================
  // SEARCH / FILTER
  // ==========================================================

  filteredSchools(): School[] {

    const search =
      this.searchText()
        .trim()
        .toLowerCase();

    const status =
      this.statusFilter();

    return this.schools()
      .filter((school) => {

        const matchesName =
          school.schoolName
            .toLowerCase()
            .includes(search);

        const matchesDise =
          school.diseCode
            .toLowerCase()
            .includes(search);

        const matchesEmail =
          school.email
            .toLowerCase()
            .includes(search);

        const matchesStatus =
          status === 'ALL' ||
          (
            status === 'ACTIVE' &&
            school.active
          ) ||
          (
            status === 'INACTIVE' &&
            !school.active
          );

        return (
          (
            matchesName ||
            matchesDise ||
            matchesEmail
          ) &&
          matchesStatus
        );

      });

  }


  onSearch(value: string): void {

    this.searchText.set(value);

  }


  onStatusChange(
    value: 'ALL' | 'ACTIVE' | 'INACTIVE'
  ): void {

    this.statusFilter.set(value);

  }


  // ==========================================================
  // ACTION MENU
  // ==========================================================

  toggleActions(
    schoolId: number
  ): void {

    if (
      this.openActionSchoolId() ===
      schoolId
    ) {

      this.openActionSchoolId.set(null);

      return;
    }

    this.openActionSchoolId.set(schoolId);

  }


  closeActions(): void {

    this.openActionSchoolId.set(null);

  }


  // ==========================================================
  // VIEW SCHOOL
  // ==========================================================

  viewSchool(
    school: School
  ): void {

    this.closeActions();

    // Immediately show the selected row.
    this.selectedSchool.set(school);

    // Set application school context.
    this.schoolService
      .setActiveSchool(school);

    // Open view modal.
    this.viewModalOpen.set(true);

    // Get latest data from backend.
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

          // Keep already loaded row data.

        }

      });

  }


  closeViewModal(): void {

    this.viewModalOpen.set(false);

    this.selectedSchool.set(null);

  }


  // ==========================================================
  // ADD SCHOOL
  // ==========================================================

  openAddSchool(): void {

    // Only Super Admin can add schools.
    if (!this.isSuperAdmin) {
      return;
    }

    // Close any open action menu.
    this.closeActions();

    // Open the reusable Add School modal.
    this.addSchoolModalOpen.set(true);

  }


  // ==========================================================
  // CLOSE REUSABLE ADD SCHOOL MODAL
  // ==========================================================

  closeAddSchoolModal(): void {

    // Close the reusable Add School modal.
    this.addSchoolModalOpen.set(false);

  }


  // ==========================================================
  // EDIT SCHOOL
  // ==========================================================

  openEditSchool(
    school: School
  ): void {

    // Only Super Admin can edit schools.
    if (!this.isSuperAdmin) {
      return;
    }

    this.closeActions();

    // Enable Edit mode.
    this.editMode.set(true);

    // Store school being edited.
    this.editingSchool.set(school);

    // Clear previous error.
    this.errorMessage.set('');

    // Populate edit form.
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

    // Open Edit modal.
    this.formModalOpen.set(true);

  }


  // ==========================================================
  // CLOSE EDIT MODAL
  // ==========================================================

  closeFormModal(): void {

    // Don't close while API is processing.
    if (this.saving()) {
      return;
    }

    this.formModalOpen.set(false);

    this.schoolForm.reset();

    this.editingSchool.set(null);

    this.editMode.set(false);

  }


  // ==========================================================
  // UPDATE SCHOOL
  // ==========================================================

  submitSchoolForm(): void {

    // Only Super Admin can update.
    if (!this.isSuperAdmin) {
      return;
    }

    // Validate edit form.
    if (this.schoolForm.invalid) {

      this.schoolForm.markAllAsTouched();

      return;
    }

    const request: SchoolRequest =
      this.schoolForm.getRawValue();

    this.saving.set(true);

    this.errorMessage.set('');


    const school =
      this.editingSchool();


    // Safety check.
    if (!school) {

      this.saving.set(false);

      return;

    }


    // Update School API.
    this.schoolService
      .updateSchool(
        school.id,
        request
      )
      .subscribe({

        next: (updatedSchool) => {

          // Close edit modal.
          this.closeFormModal();

          // Update active school context.
          this.schoolService
            .setActiveSchool(updatedSchool);

          // Show success message.
          this.successMessage.set(
            'School updated successfully.'
          );

          // Refresh list.
          this.loadSchools();

          // Stop saving state.
          this.saving.set(false);

        },

        error: (
          error: HttpErrorResponse
        ) => {

          console.error(
            'Failed to update school',
            error
          );

          this.saving.set(false);

          this.errorMessage.set(
            this.getApiErrorMessage(error)
          );

        }

      });

  }


  // ==========================================================
  // API ERROR MESSAGE
  // ==========================================================

  private getApiErrorMessage(
    error: HttpErrorResponse
  ): string {

    // --------------------------------------------------------
    // Direct backend message.
    // --------------------------------------------------------

    if (error.error?.message) {

      return error.error.message;

    }


    // --------------------------------------------------------
    // Validation errors.
    // --------------------------------------------------------

    if (error.error?.errors) {

      // Validation errors returned as an array.
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


      // Validation errors returned as an object.
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


    // --------------------------------------------------------
    // Spring fieldErrors.
    // --------------------------------------------------------

    if (
      error.error?.fieldErrors &&
      typeof error.error.fieldErrors ===
      'object'
    ) {

      const messages =
        Object.values(
          error.error.fieldErrors
        )
          .filter(Boolean)
          .map(
            value => String(value)
          );

      if (messages.length > 0) {

        return messages.join(', ');

      }

    }


    // --------------------------------------------------------
    // Spring validation detail.
    // --------------------------------------------------------

    if (error.error?.detail) {

      return error.error.detail;

    }


    // --------------------------------------------------------
    // Final fallback.
    // --------------------------------------------------------

    return (
      'Unable to process the school request. ' +
      'Please try again.'
    );

  }


  // ==========================================================
  // ACTIVATE / DEACTIVATE CONFIRMATION
  // ==========================================================

  confirmStatusChange(
    school: School
  ): void {

    // Only Super Admin can change status.
    if (!this.isSuperAdmin) {
      return;
    }

    this.closeActions();

    this.pendingStatusSchool.set(school);

    this.confirmationModalOpen.set(true);

    this.errorMessage.set('');

  }


  closeConfirmationModal(): void {

    // Don't close while processing.
    if (this.saving()) {
      return;
    }

    this.confirmationModalOpen.set(false);

    this.pendingStatusSchool.set(null);

  }


  // ==========================================================
  // EXECUTE ACTIVATE / DEACTIVATE
  // ==========================================================

  executeStatusChange(): void {

    const school =
      this.pendingStatusSchool();

    if (!school) {
      return;
    }

    // Only Super Admin.
    if (!this.isSuperAdmin) {
      return;
    }

    this.saving.set(true);

    this.errorMessage.set('');


    // Active -> Deactivate.
    // Inactive -> Activate.
    const request$ =
      school.active

        ? this.schoolService
          .deactivateSchool(
            school.id
          )

        : this.schoolService
          .activateSchool(
            school.id
          );


    request$.subscribe({

      // ------------------------------------------------------
      // SUCCESS
      // ------------------------------------------------------

      next: (updatedSchool) => {

        this.confirmationModalOpen
          .set(false);

        this.pendingStatusSchool
          .set(null);

        this.schoolService
          .setActiveSchool(updatedSchool);

        this.successMessage.set(
          school.active
            ? 'School deactivated successfully.'
            : 'School activated successfully.'
        );

        // Refresh list.
        this.loadSchools();

        this.saving.set(false);

      },


      // ------------------------------------------------------
      // ERROR
      // ------------------------------------------------------

      error: (
        error: HttpErrorResponse
      ) => {

        console.error(
          'Failed to change school status',
          error
        );

        this.saving.set(false);

        this.errorMessage.set(
          this.getApiErrorMessage(error)
        );

      }

    });

  }


  // ==========================================================
  // FORM VALIDATION - EDIT
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
  // SCHOOL CREATED EVENT
  // ==========================================================

  onSchoolCreated(
    createdSchool: School
  ): void {

    // Close reusable Add School modal.
    this.addSchoolModalOpen.set(false);

    // Set newly created school as active context.
    this.schoolService
      .setActiveSchool(createdSchool);

    // Show success message.
    this.successMessage.set(
      'School created successfully.'
    );

    // Reload school list.
    this.loadSchools();

  }

}