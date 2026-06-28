import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-dashboard-header',
  standalone: true,
  imports: [MatIconModule],
  templateUrl: './dashboard-header.component.html',
  styleUrl: './dashboard-header.component.css'
})
export class DashboardHeaderComponent {

  @Input() appName: string = 'EduForge';
  @Input() userName: string = '';
  @Output() menuClicked: EventEmitter<void> = new EventEmitter<void>();
  onMenuClick(): void {
    this.menuClicked.emit();
  }
}
