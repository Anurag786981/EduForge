export interface User {
  userId: number;
  firstName: string;
  lastName: string;
  email: string;
  roleId: number;
  role: string;
  schoolId: number;
  school: string;
  active: boolean;
  createdAt: string;
  updatedAt: string;
  accountLocked: boolean;
  lastLoginAt: string | null;
}