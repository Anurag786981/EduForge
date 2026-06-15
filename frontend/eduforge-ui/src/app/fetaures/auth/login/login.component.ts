import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
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
    MatIconModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginForm: FormGroup;

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
      ]]
    });
  }

 onSubmit(): void {

  console.log('Login button clicked');

  if (this.loginForm.invalid) {
    console.log('Form is invalid');
    this.loginForm.markAllAsTouched();
    return;
  }

  const loginRequest = this.loginForm.value;

  console.log('Request Payload:', loginRequest);

  this.authService.login(loginRequest)
    .subscribe({
      next: (response) => {
        console.log('Success Response:', response);
      },
      error: (error) => {
        console.log('Error Response:', error);
      }
    });
}
}