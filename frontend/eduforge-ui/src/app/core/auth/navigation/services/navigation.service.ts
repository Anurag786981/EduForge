import { Injectable, signal } from '@angular/core';

import { NavigationSection } from '../models/navigation-section.model';
import { SUPER_ADMIN_NAVIGATION } from '../config/super-admin.navigation';

@Injectable({
  providedIn: 'root'
})
export class NavigationService {

  readonly navigation =
    signal<NavigationSection[]>(SUPER_ADMIN_NAVIGATION);

}