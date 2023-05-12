package com.ecore.roleapi.repositories;

import com.ecore.roleapi.beans.roles.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles,String> {
    Optional<Roles> findById(String id);
    Roles findByIsDefault(Boolean isDefault);
}
