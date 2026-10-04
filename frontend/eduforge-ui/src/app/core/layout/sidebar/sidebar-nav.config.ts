import { NavItem } from '../../shared/models/nav-item.model';

/**
 * Static sidebar navigation configuration.
 *
 * SUPER_ADMIN has full platform access.
 *
 * School-scoped roles only see the modules that belong
 * to their school-level responsibilities.
 */
export const SIDEBAR_NAV_ITEMS: NavItem[] = [

  // Dashboard is available to all authenticated users.
  {
    id: 'dashboard',
    label: 'Dashboard',
    icon: 'dashboard',
    route: '/dashboard'
  },

  // Super Admin manages all schools on the platform.
  {
    id: 'schools',
    label: 'Schools',
    icon: 'apartment',
    route: '/schools',
    allowedRoles: ['SUPER_ADMIN']
  },

  // Super Admin manages school administrator accounts.
  {
    id: 'school-admins',
    label: 'School Admins',
    icon: 'admin_panel_settings',
    route: '/school-admins',
    allowedRoles: ['SUPER_ADMIN']
  },

  // Teachers are school-level resources.
  {
    id: 'teachers',
    label: 'Teachers',
    icon: 'school',
    route: '/teachers',
    allowedRoles: [
      'SUPER_ADMIN',
      'SCHOOL_ADMIN'
    ]
  },

  // Students are school-level resources.
  {
    id: 'students',
    label: 'Students',
    icon: 'groups',
    route: '/students',
    allowedRoles: [
      'SUPER_ADMIN',
      'SCHOOL_ADMIN'
    ]
  },

  // Super Admin has platform-level user management.
  {
    id: 'users',
    label: 'Users',
    icon: 'people',
    route: '/users',
    allowedRoles: ['SUPER_ADMIN']
  },

  // Role and permission management belongs to Super Admin.
  {
    id: 'roles-permissions',
    label: 'Roles & Permissions',
    icon: 'shield',
    route: '/roles-permissions',
    allowedRoles: ['SUPER_ADMIN']
  },

  // Attendance is available at platform and school level.
  {
    id: 'attendance',
    label: 'Attendance',
    icon: 'event_available',
    route: '/attendance',
    allowedRoles: [
      'SUPER_ADMIN',
      'SCHOOL_ADMIN'
    ]
  },

  // Examination is available at platform and school level.
  {
    id: 'examination',
    label: 'Examination',
    icon: 'quiz',
    route: '/examination',
    allowedRoles: [
      'SUPER_ADMIN',
      'SCHOOL_ADMIN'
    ]
  },

  // Reports are available at platform and school level.
  {
    id: 'reports',
    label: 'Reports',
    icon: 'bar_chart',
    route: '/reports',
    allowedRoles: [
      'SUPER_ADMIN',
      'SCHOOL_ADMIN'
    ]
  },

  // Billing is currently a Super Admin platform feature.
  {
    id: 'billing',
    label: 'Billing',
    icon: 'credit_card',
    route: '/billing',
    allowedRoles: ['SUPER_ADMIN']
  },

  // Support is available to both roles.
  {
    id: 'support',
    label: 'Support',
    icon: 'support_agent',
    route: '/support',
    allowedRoles: [
      'SUPER_ADMIN',
      'SCHOOL_ADMIN'
    ]
  },

  // Settings is available to both roles.
  {
    id: 'settings',
    label: 'Settings',
    icon: 'settings',
    route: '/settings',
    allowedRoles: [
      'SUPER_ADMIN',
      'SCHOOL_ADMIN'
    ]
  }

];