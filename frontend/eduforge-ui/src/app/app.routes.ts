import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import {TeacherSidebarComponent} from './features/teacher/teacher-dashboard/teacher-sidebar/teacher-sidebar.component';


export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'teacher-sidebar',
    component: TeacherSidebarComponent
  }
];
