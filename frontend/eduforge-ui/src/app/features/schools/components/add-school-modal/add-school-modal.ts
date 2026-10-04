import {
  Component,
  EventEmitter,
  Output,
  inject,
  signal
} from '@angular/core';

import {
  FormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import { HttpErrorResponse } from '@angular/common/http';

import {
  SchoolRequest,
  SchoolService
} from '../../../../core/services/school';


@Component({
  selector: 'app-add-school-modal',

  imports: [
    ReactiveFormsModule
  ],

  templateUrl: './add-school-modal.html',

  styleUrl: './add-school-modal.scss'
})
export class AddSchoolModal {

  // ==========================================================
  // SERVICES
  // ==========================================================

  private readonly fb =
    inject(FormBuilder);

  private readonly schoolService =
    inject(SchoolService);


  // ==========================================================
  // COMPONENT EVENTS
  // ==========================================================

  /**
   * Sends the newly created school back to the parent.
   *
   * Dashboard can use this to refresh KPIs.
   *
   * Schools page can use this to refresh the school table.
   */
  @Output()
  readonly created =
    new EventEmitter<any>();


  /**
   * Tells the parent that the modal should be closed.
   */
  @Output()
  readonly closed =
    new EventEmitter<void>();


  // ==========================================================
  // COMPONENT STATE
  // ==========================================================

  // Prevents duplicate API requests.
  readonly saving =
    signal(false);

  // API validation/error message.
  readonly errorMessage =
    signal('');


  // ==========================================================
  // REACTIVE FORM
  // ==========================================================

  /**
   * School creation form.
   *
   * Validation happens on the Angular side first.
   * Backend validation remains the final protection.
   */
  readonly schoolForm =
    this.fb.nonNullable.group({

      // School name.
      schoolName: [
        '',
        [
          Validators.required,
          Validators.maxLength(100)
        ]
      ],

      // DISE must contain exactly 11 digits.
      diseCode: [
        '',
        [
          Validators.required,
          Validators.pattern(/^\d{11}$/)
        ]
      ],

      // School email.
      email: [
        '',
        [
          Validators.required,
          Validators.email,
          Validators.maxLength(100)
        ]
      ],

      // Indian mobile number.
      //
      // First digit must be 6-9.
      // Total length must be exactly 10 digits.
      phoneNumber: [
        '',
        [
          Validators.required,
          Validators.pattern(/^[6-9][0-9]{9}$/)
        ]
      ],

      // School address.
      address: [
        '',
        [
          Validators.required,
          Validators.maxLength(250)
        ]
      ]

    });


  // ==========================================================
  // CREATE SCHOOL
  // ==========================================================

  submit(): void {

    // Prevent submission while another request is running.
    if (this.saving()) {
      return;
    }


    // Check Angular Reactive Form validation.
    if (this.schoolForm.invalid) {

      // Mark all fields as touched so validation
      // messages become visible.
      this.schoolForm.markAllAsTouched();

      return;
    }


    // Get strongly typed form data.
    const request: SchoolRequest =
      this.schoolForm.getRawValue();


    // Start saving state.
    this.saving.set(true);

    // Clear any previous API error.
    this.errorMessage.set('');


    // Call backend Create School API.
    this.schoolService
      .createSchool(request)
      .subscribe({

        // ----------------------------------------------------
        // SUCCESS
        // ----------------------------------------------------

        next: (createdSchool) => {

          // Stop saving state.
          this.saving.set(false);

          // Notify parent about the newly created school.
          this.created.emit(createdSchool);

          // Tell parent to close the modal.
          this.closed.emit();

          // Reset form for next use.
          this.schoolForm.reset();
        },


        // ----------------------------------------------------
        // ERROR
        // ----------------------------------------------------

        error: (
          error: HttpErrorResponse
        ) => {

          console.error(
            'Failed to create school',
            error
          );

          // Stop saving state.
          this.saving.set(false);

          // Display backend validation/error message.
          this.errorMessage.set(
            this.getApiErrorMessage(error)
          );
        }

      });
  }


  // ==========================================================
  // CLOSE MODAL
  // ==========================================================

  close(): void {

    // Don't close while API request is running.
    if (this.saving()) {
      return;
    }

    // Tell parent to close the modal.
    this.closed.emit();

    // Reset the form.
    this.schoolForm.reset();

    // Clear previous error.
    this.errorMessage.set('');
  }


  // ==========================================================
  // FIELD VALIDATION
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
      this.schoolForm.get(fieldName);

    return !!control &&
      control.invalid &&
      (
        control.touched ||
        control.dirty
      );
  }


  // ==========================================================
  // API ERROR HANDLING
  // ==========================================================

  private getApiErrorMessage(
    error: HttpErrorResponse
  ): string {

    // Direct backend message.
    if (error.error?.message) {

      return error.error.message;
    }


    // Backend validation errors.
    if (error.error?.errors) {

      // Validation errors returned as an array.
      if (
        Array.isArray(error.error.errors)
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
        typeof error.error.errors === 'object'
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


    // Spring field validation errors.
    if (
      error.error?.fieldErrors &&
      typeof error.error.fieldErrors === 'object'
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


    // Spring ProblemDetail-style response.
    if (error.error?.detail) {

      return error.error.detail;
    }


    // Final fallback.
    return (
      'Unable to create school. ' +
      'Please try again.'
    );
  }

}