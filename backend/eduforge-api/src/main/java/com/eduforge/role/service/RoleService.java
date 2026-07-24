package com.eduforge.role.service;

import com.eduforge.role.dto.RoleRequest;
import com.eduforge.role.dto.RoleResponse;
import java.util.List;

public interface RoleService {

  RoleResponse createRole(RoleRequest roleRequest);

  RoleResponse getRoleById(Long roleId);

  List<RoleResponse> getAllRoles();

  RoleResponse updateRole(Long roleId, RoleRequest roleRequest);

  RoleResponse activateRole(Long roleId);

  RoleResponse deActivateRole(Long roleId);
}
