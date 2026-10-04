import { NavigationSection } from '../models/navigation-section.model';

export const SUPER_ADMIN_NAVIGATION: NavigationSection[] = [

  {
    title: 'Platform',
    items: [
      {
        id: 'dashboard',
        title: 'Dashboard',
        icon: 'dashboard',
        route: '/dashboard'
      }
    ]
  },

  {
    title: 'School Management',
    items: [
      {
        id: 'schools',
        title: 'Schools',
        icon: 'apartment',
        route: '/schools'
      },
      {
        id: 'school-admins',
        title: 'School Admins',
        icon: 'admin_panel_settings',
        route: '/school-admins'
      }
    ]
  },

  {
    title: 'Users',
    items: [
      {
        id: 'teachers',
        title: 'Teachers',
        icon: 'school',
        route: '/teachers'
      },
      {
        id: 'students',
        title: 'Students',
        icon: 'groups',
        route: '/students'
      },
      {
        id: 'users',
        title: 'Users',
        icon: 'person',
        route: '/users'
      }
    ]
  },

  {
    title: 'Administration',
    items: [
      {
        id: 'roles',
        title: 'Roles & Permissions',
        icon: 'security',
        route: '/roles'
      }
    ]
  }

];