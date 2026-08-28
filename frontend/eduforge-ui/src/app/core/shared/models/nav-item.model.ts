/**
 * Shared navigation contract used by the Sidebar
 * and other navigation-aware components.
 */
export interface NavItem {

  // Unique identifier for the navigation item.
  id: string;

  // Label displayed in the sidebar.
  label: string;

  // Material icon name displayed beside the label.
  icon: string;

  // Absolute Angular route for the navigation item.
  route: string;

  // Optional badge count displayed beside the item.
  badge?: number;

  // Roles allowed to see this navigation item.
  allowedRoles?: string[];
}