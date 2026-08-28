export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  success: boolean;
  message: string;
  data: LoginUser;
}

export interface LoginUser {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  role: string;
  token: string;
  schoolId: number | null;
  schoolName: string | null;
  active: boolean;
  mustChangePassword: boolean;
}