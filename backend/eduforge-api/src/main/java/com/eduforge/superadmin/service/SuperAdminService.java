package com.eduforge.superadmin.service;

import com.eduforge.superadmin.dto.CreateSuperAdminRequest;
import com.eduforge.superadmin.dto.SuperAdminResponse;
import com.eduforge.superadmin.dto.UpdateAdminStatusRequest;
import com.eduforge.superadmin.dto.UpdateSuperAdminRequest;
import java.util.List;

public interface SuperAdminService {

  SuperAdminResponse createSuperAdmin(CreateSuperAdminRequest request);

  List<SuperAdminResponse> getAllSuperAdmins();

  SuperAdminResponse getSuperAdminById(Long id);

  SuperAdminResponse updateSuperAdmin(Long id, UpdateSuperAdminRequest request);

  SuperAdminResponse updateStatus(Long id, UpdateAdminStatusRequest request);
}
