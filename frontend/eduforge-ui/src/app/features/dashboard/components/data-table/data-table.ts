// ============================================================
// EduForge - Dashboard Data Table
// ============================================================

import {
  Component,
  Input,
  Output,
  EventEmitter,
  inject
} from '@angular/core';

import { DatePipe } from '@angular/common';

import { Router } from '@angular/router';

import { School } from '../../../../core/models/school';


@Component({
  selector: 'app-data-table',

  imports: [
    DatePipe
  ],

  templateUrl: './data-table.html',

  styleUrl: './data-table.scss',
})
export class DataTable {

  // ==========================================================
  // SERVICES
  // ==========================================================

  private readonly router =
    inject(Router);


  // ==========================================================
  // INPUT
  // ==========================================================

  @Input({ required: true })
  schools: School[] = [];


  // ==========================================================
  // OUTPUT EVENTS
  // ==========================================================

  @Output()
  view =
    new EventEmitter<School>();

  @Output()
  edit =
    new EventEmitter<School>();

  @Output()
  statusChange =
    new EventEmitter<School>();


  // ==========================================================
  // ACTION MENU STATE
  // ==========================================================

  openActionSchoolId:
    number | null = null;


  // ==========================================================
  // VIEW ALL
  // ==========================================================

  viewAllSchools(): void {

    this.router.navigate([
      '/schools'
    ]);

  }


  // ==========================================================
  // ACTION MENU
  // ==========================================================

  toggleActions(
    schoolId: number
  ): void {

    if (
      this.openActionSchoolId ===
      schoolId
    ) {

      this.openActionSchoolId =
        null;

      return;

    }

    this.openActionSchoolId =
      schoolId;

  }


  closeActions(): void {

    this.openActionSchoolId =
      null;

  }


  // ==========================================================
  // VIEW
  // ==========================================================

  onView(
    school: School
  ): void {

    this.closeActions();

    this.view.emit(school);

  }


  // ==========================================================
  // EDIT
  // ==========================================================

  onEdit(
    school: School
  ): void {

    this.closeActions();

    this.edit.emit(school);

  }


  // ==========================================================
  // ACTIVATE / DEACTIVATE
  // ==========================================================

  onStatusChange(
    school: School
  ): void {

    this.closeActions();

    this.statusChange.emit(
      school
    );

  }

}