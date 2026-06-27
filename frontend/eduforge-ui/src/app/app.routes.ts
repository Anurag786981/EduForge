import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import {TeacherSidebarComponent} from './features/teacher/teacher-sidebar/teacher-sidebar.component';
import {TeacherDashboardComponent} from './features/teacher/teacher-dashboard/teacher-dashboard.component';
import { TodayComponent } from './features/teacher/pages/today/today.component';
import { teacherChildRoutes } from './teacher.routes';


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
    component: TeacherDashboardComponent,
    children: teacherChildRoutes
  }
];
