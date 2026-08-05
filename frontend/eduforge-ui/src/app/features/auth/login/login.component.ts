import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import{MatCheckboxModule} from '@angular/material/checkbox';
import{MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { AuthService } from '../../../features/auth/services/auth.service';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatCheckboxModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent {

  loginForm: FormGroup;
  successMessage: string = '';
  errorMessage: string = '';
  isLoading: boolean = false; // Loading state variable

  hidePassword: boolean = true;

  constructor(private fb: FormBuilder,private authService: AuthService) {
    this.loginForm = this.fb.group({
      email: ['', [
        Validators.required,
        Validators.email
      ]],
      password: ['', [
        Validators.required,
        Validators.minLength(8)
      ]],
      rememberMe: [false] // Add a form control for the "Remember Me" checkbox
    });
  }
  

 onSubmit(): void {

  console.log('Login button clicked');

  if (this.loginForm.invalid) {
    console.log('Form is invalid');
    this.loginForm.markAllAsTouched();
    return;
  }
  this.isLoading = true; // Set loading state to true when the form is submitted

  const loginRequest = this.loginForm.value;

  console.log('Request Payload:', loginRequest);

  this.authService.login(loginRequest)
  .subscribe({

    next: (response) => {

      this.successMessage = response.message;
      this.errorMessage = '';
      this.isLoading = false; // Reset loading state on success

      console.log('Success Response:', response);

      // We will navigate to dashboard after JWT implementation
      // this.router.navigate(['/dashboard']);
    },

    error: (error) => {
      this.isLoading = false; // Reset loading state on error

      this.successMessage = '';

      if (error.status === 0) {
        this.errorMessage = 'Unable to connect to the server. Please check your network connection.';

       }else  if (error.error && error.error.message) {
        this.errorMessage = error.error.message;
      } else {
        this.errorMessage = 'Something went wrong. Please try again.';
      }

      console.log('Error Response:', error);
    }

  });
}
}