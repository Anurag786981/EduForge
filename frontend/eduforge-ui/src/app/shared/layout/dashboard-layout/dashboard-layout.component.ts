import { Component, Input } from '@angular/core';
import {CommonModule} from '@angular/common';
import { RouterOutlet } from '@angular/router';
import {DashboardHeaderComponent} from '../../dashboard-header/dashboard-header.component';

@Component({
  selector: 'app-dashboard-layout',
  imports: [
    CommonModule,
    RouterOutlet,
    DashboardHeaderComponent
  ],
  templateUrl: './dashboard-layout.component.html',
  styleUrl: './dashboard-layout.component.css'
})
export class DashboardLayoutComponent {
  sidebarOpen = false;

    toggleSidebar(): void {

        this.sidebarOpen = !this.sidebarOpen;

    }

    closeSidebar(): void {

        this.sidebarOpen = false;

    }

    @Input() appName: string = 'EduForge';
    @Input() appLogo: string = 'assets/images/logo.png';
    @Input() userName: string = '';
}
