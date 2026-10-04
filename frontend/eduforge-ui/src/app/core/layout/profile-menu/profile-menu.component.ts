import { ChangeDetectionStrategy, Component, input, output } from '@angular/core';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';

export type UserStatus = 'online' | 'away' | 'offline';

@Component({
  selector: 'ef-profile-menu',
  standalone: true,
  imports: [
    MatMenuModule,
    MatIconModule,
    MatDividerModule
  ],
  templateUrl: './profile-menu.component.html',
  styleUrl: './profile-menu.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProfileMenuComponent {

  readonly userName = input.required<string>();

  readonly userRole = input.required<string>();

  /**
   * Current user availability status.
   *
   * online  -> green
   * away    -> orange
   * offline -> red
   *
   * Default is online so the current UI continues
   * to work even before backend presence is connected.
   */
  readonly status = input<UserStatus>('online');

  readonly logout = output<void>();

  protected get initials(): string {
    return this.userName()
      .split(' ')
      .filter(Boolean)
      .slice(0, 2)
      .map((part) => part[0]?.toUpperCase())
      .join('');
  }

  protected get statusLabel(): string {
    switch (this.status()) {
      case 'online':
        return 'Online';

      case 'away':
        return 'Away';

      case 'offline':
        return 'Offline';

      default:
        return 'Offline';
    }
  }
}