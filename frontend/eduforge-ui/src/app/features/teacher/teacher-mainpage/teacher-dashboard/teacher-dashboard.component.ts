import { Component } from '@angular/core';
import { TeacherSidebarComponent } from '../teacher-sidebar/teacher-sidebar.component';
import { TeacherDashboardKpisComponent } from './components/teacher-dashboard-kpis/teacher-dashboard-kpis.component';

@Component({
  selector: 'app-teacher-dashboard',
  standalone: true,
  imports: [
    TeacherSidebarComponent, 
    TeacherDashboardKpisComponent
  ],
  templateUrl: './teacher-dashboard.component.html',
  styleUrl: './teacher-dashboard.component.css'
})
export class TeacherDashboardComponent {

}
