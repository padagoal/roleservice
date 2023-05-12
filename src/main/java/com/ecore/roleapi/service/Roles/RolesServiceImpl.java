package com.ecore.roleapi.service.Roles;

import com.ecore.roleapi.beans.roles.Roles;
import com.ecore.roleapi.beans.roles.RolesRequestDTO;
import com.ecore.roleapi.beans.roles.RolesResponseDTO;
import com.ecore.roleapi.exception.ResourceNotFoundException;
import com.ecore.roleapi.repositories.RolesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class RolesServiceImpl implements RolesService {

    private final RolesRepository rolesRepository;
    private final ModelMapper modelMapper;

    @Override
    public RolesResponseDTO createRole(RolesRequestDTO rolesRequestDTO){
        Roles rol = modelMapper.map(rolesRequestDTO,Roles.class);
        RolesResponseDTO rolesResponseDTO = new RolesResponseDTO();
        try{
            rol = rolesRepository.save(rol);
            rolesResponseDTO = modelMapper.map(rol,RolesResponseDTO.class);
            log.info("New Role Created: ID:[{}] - Name: [{}]",rol.getId(),rol.getName());
        }catch (Exception e){
            log.error("Exception occur trying to save a new ROL ",e);
        }
        return rolesResponseDTO;
    }

    @Override
    public Roles searchRole(String id){
        Optional<Roles> rolesOptional = Optional.empty();
        Roles roleById = null;
        if(id!=null && !Objects.requireNonNull(id).equalsIgnoreCase("")) {
            try {
                rolesOptional = rolesRepository.findById(id);
            } catch (Exception e) {
                log.error("An error occur when try to search rol in database", e);
            }

            if (rolesOptional.isEmpty()) {
                log.warn("The given ID for Roles doesn't exists - ID:[{}]", id);
                throw new ResourceNotFoundException(String.format("The given ID for Roles doesn't exists - ID:[%s]", id));
            } else {
                roleById = rolesOptional.get();
            }
        }else{
            log.warn("The given rolId for search is null or blank");
        }
        return roleById;

    }

    /**
     * This method searchs and return the Rol that has set as default (in database, has the isDefault set on True)
     * @return Roles
     */
    @Override
    public Roles searchDefaultRole() {
        return rolesRepository.findByIsDefault(true);
    }

    /**
     * This method return all the roles
     * @return
     */
    @Override
    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }




}
