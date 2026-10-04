// Import Angular dependency injection and signals.
import { Injectable, inject, signal } from '@angular/core';

// Import HttpClient for School API calls.
import { HttpClient } from '@angular/common/http';

// Import Observable for typed API responses.
import { Observable } from 'rxjs';

// Import the School model.
import { School } from '../models/school';


// Define the request payload used by Create and Update School APIs.
export interface SchoolRequest {
  schoolName: string;
  diseCode: string;
  email: string;
  phoneNumber: string;
  address: string;
}


@Injectable({
  // Create one shared SchoolService for the application.
  providedIn: 'root'
})
export class SchoolService {

  // Inject Angular HttpClient.
  private readonly http = inject(HttpClient);

  // Store the base URL of the School APIs.
  private readonly apiUrl = 'http://localhost:8080/api/schools';


  // Get all schools for Super Admin.
  getAllSchools(): Observable<School[]> {
    return this.http.get<School[]>(this.apiUrl);
  }


  // Get the school assigned to the logged-in school user.
  getMySchool(): Observable<School> {
    return this.http.get<School>(`${this.apiUrl}/my-school`);
  }


  // Get one school by its ID.
  getSchoolById(schoolId: number): Observable<School> {
    return this.http.get<School>(`${this.apiUrl}/${schoolId}`);
  }


  // Create a new school.
  createSchool(request: SchoolRequest): Observable<School> {
    return this.http.post<School>(
      `${this.apiUrl}/registerSchool`,
      request
    );
  }


  // Update an existing school.
  updateSchool(
    schoolId: number,
    request: SchoolRequest
  ): Observable<School> {
    return this.http.put<School>(
      `${this.apiUrl}/${schoolId}`,
      request
    );
  }


  // Activate a school.
  activateSchool(schoolId: number): Observable<School> {
    return this.http.patch<School>(
      `${this.apiUrl}/${schoolId}/activate`,
      {}
    );
  }


  // Deactivate a school.
  deactivateSchool(schoolId: number): Observable<School> {
    return this.http.patch<School>(
      `${this.apiUrl}/${schoolId}/deactivate`,
      {}
    );
  }


  // Store the currently active school context.
  private readonly _activeSchool = signal<School | null>(null);


  // Expose the active school as read-only state.
  readonly activeSchool = this._activeSchool.asReadonly();


  // Set the currently active school.
  setActiveSchool(school: School): void {
    this._activeSchool.set(school);
  }
}