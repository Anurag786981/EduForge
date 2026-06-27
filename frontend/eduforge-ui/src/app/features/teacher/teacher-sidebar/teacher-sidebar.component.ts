import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-teacher-sidebar',
  standalone: true,
  imports: [ RouterLink, RouterLinkActive, CommonModule, MatIconModule],
  templateUrl: './teacher-sidebar.component.html',
  styleUrl: './teacher-sidebar.component.css',
  
})
export class TeacherSidebarComponent {
  menuItems = [
    { icon: 'home', label: 'Home', route: 'home' },
    { icon: 'today', label: 'Today', route: 'today' },
    { icon: 'calendar_month', label: 'Timetable', route: 'timetable' },  
    { icon: 'fact_check', label: 'Attendance', route: 'attendance' },
    { icon: 'analytics', label: 'Analytics', route: 'classanalytics' },
    { icon: 'assignments', label: 'Assignments', route: 'assignments' },
    { icon: 'school', label: 'Exams', route: 'exams' },
    { icon: 'event', label: 'Activities', route: 'classactivities' },
    { icon: 'account_circle', label: 'My Profile', route: 'personal' },
  ]
}
