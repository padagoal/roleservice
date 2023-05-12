package com.ecore.roleapi.service.Roles;


import com.ecore.roleapi.beans.membership.Membership;
import com.ecore.roleapi.beans.roles.Roles;
import com.ecore.roleapi.beans.roles.RolesRequestDTO;
import com.ecore.roleapi.beans.roles.RolesResponseDTO;
import java.util.List;


public interface RolesService {

    RolesResponseDTO createRole(RolesRequestDTO rolesRequestDTO);

    Roles searchRole(String id);

    Roles searchDefaultRole();

    List<Roles> getAllRoles();



}
