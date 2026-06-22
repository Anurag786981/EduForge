import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import {TeacherSidebarComponent} from './features/teacher/teacher-mainpage/teacher-sidebar/teacher-sidebar.component';
import {TeacherDashboardComponent} from './features/teacher/teacher-mainpage/teacher-dashboard/teacher-dashboard.component';

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
    path: 'teacher-dashboard',
    component: TeacherDashboardComponent
  },
  {
    path: 'teacher-sidebar',
    component: TeacherSidebarComponent
  }
];
