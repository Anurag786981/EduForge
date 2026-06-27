import { Component } from '@angular/core';
import {RouterOutlet} from '@angular/router';
import { CommonModule } from '@angular/common';
import { TeacherSidebarComponent } from '../teacher-sidebar/teacher-sidebar.component';
import { DashboardHeaderComponent } from '../../../shared/dashboard-header/dashboard-header.component';
import { DashboardLayoutComponent } from '../../../shared/layout/dashboard-layout/dashboard-layout.component';



@Component({
  selector: 'app-teacher-dashboard',
  standalone: true,
  imports: [
        CommonModule,
        RouterOutlet,
        TeacherSidebarComponent,
        DashboardHeaderComponent,
        DashboardLayoutComponent
  ],
  templateUrl: './teacher-dashboard.component.html',
  styleUrl: './teacher-dashboard.component.css'
})

export class TeacherDashboardComponent {

    sidebarOpen = false;

    toggleSidebar(): void {

        this.sidebarOpen = !this.sidebarOpen;

    }

    closeSidebar(): void {

        this.sidebarOpen = false;

    }

}
