import { Component, Input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-dashboard-stat-cards',
  standalone: true,
  imports: [
    MatIconModule
  ],
  templateUrl: './dashboard-stat-cards.html',
  styleUrl: './dashboard-stat-cards.scss'
})
export class DashboardStatCards {

  // KPI data displayed on the Super Admin dashboard.
  @Input() stats: any[] = [];

}