import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { BreadcrumbService } from './breadcrumb.service';

/**
 * Route-aware breadcrumb strip. Purely presentational — all trail
 * derivation happens in BreadcrumbService so this component stays dumb
 * and easy to reuse/test.
 */
@Component({
  selector: 'ef-breadcrumb',
  standalone: true,
  imports: [RouterLink, MatIconModule],
  templateUrl: './breadcrumb.component.html',
  styleUrl: './breadcrumb.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BreadcrumbComponent {
  protected readonly breadcrumbService = inject(BreadcrumbService);
}
