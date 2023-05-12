package com.ecore.roleapi.service.Roles;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ecore.roleapi.beans.roles.Roles;
import com.ecore.roleapi.beans.roles.RolesRequestDTO;
import com.ecore.roleapi.exception.ResourceNotFoundException;
import com.ecore.roleapi.repositories.RolesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.management.AttributeNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RolesServiceImpl.class})
@ExtendWith(SpringExtension.class)
class RolesServiceImplTest {
    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private RolesRepository rolesRepository;

    @Autowired
    private RolesServiceImpl rolesServiceImpl;

    @Test
    void testCreateRole() {
        Roles roles = new Roles();
        roles.setIsDefault(true);
        roles.setId("42");
        roles.setName("Name");
        when(this.rolesRepository.save((Roles) any())).thenReturn(roles);

        Roles roles1 = new Roles();
        roles1.setIsDefault(true);
        roles1.setId("42");
        roles1.setName("Name");
        when(this.modelMapper.map((Object) any(), (Class<Object>) any())).thenReturn(roles1);
        this.rolesServiceImpl.createRole(new RolesRequestDTO("Name"));
        verify(this.rolesRepository).save((Roles) any());
        verify(this.modelMapper, atLeast(1)).map((Object) any(), (Class<Object>) any());
        assertTrue(this.rolesServiceImpl.getAllRoles().isEmpty());
    }

    @Test
    void testCreateRole_throwsResourceNotFound() {
        when(this.modelMapper.map((Object) any(), (Class<Object>) any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        assertThrows(ResourceNotFoundException.class, () -> this.rolesServiceImpl.createRole(new RolesRequestDTO("Name")));
        verify(this.modelMapper).map((Object) any(), (Class<Object>) any());
    }


    @Test
    void testSearchRole(){
        Roles roles = new Roles();
        roles.setIsDefault(true);
        roles.setId("42");
        roles.setName("Name");
        Optional<Roles> ofResult = Optional.of(roles);
        when(this.rolesRepository.findById((String) any())).thenReturn(ofResult);
        assertSame(roles, this.rolesServiceImpl.searchRole("42"));
        verify(this.rolesRepository).findById((String) any());
        assertTrue(this.rolesServiceImpl.getAllRoles().isEmpty());
    }

    @Test
    void testSearchRole_throwsResourceNotFoundException() throws ResourceNotFoundException {
        when(this.rolesRepository.findById((String) any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> this.rolesServiceImpl.searchRole("42"));
        verify(this.rolesRepository).findById((String) any());
    }

    @Test
    void testSearchRole_paramNull() throws AttributeNotFoundException {
        Roles roles = new Roles();
        roles.setIsDefault(true);
        roles.setId("42");
        roles.setName("Name");
        Optional<Roles> ofResult = Optional.of(roles);
        when(this.rolesRepository.findById((String) any())).thenReturn(ofResult);
        assertNull(this.rolesServiceImpl.searchRole(null));
    }


    @Test
    void testSearchRole_paramEmptyString() {
        Roles roles = new Roles();
        roles.setIsDefault(true);
        roles.setId("42");
        roles.setName("Name");
        Optional<Roles> ofResult = Optional.of(roles);
        when(this.rolesRepository.findById((String) any())).thenReturn(ofResult);
        assertNull(this.rolesServiceImpl.searchRole(""));
    }


    @Test
    void testSearchDefaultRole() {
        Roles roles = new Roles();
        roles.setIsDefault(true);
        roles.setId("42");
        roles.setName("Name");
        when(this.rolesRepository.findByIsDefault((Boolean) any())).thenReturn(roles);
        assertSame(roles, this.rolesServiceImpl.searchDefaultRole());
        verify(this.rolesRepository).findByIsDefault((Boolean) any());
        assertTrue(this.rolesServiceImpl.getAllRoles().isEmpty());
    }


    @Test
    void testGetAllRoles() {
        ArrayList<Roles> rolesList = new ArrayList<>();
        when(this.rolesRepository.findAll()).thenReturn(rolesList);
        List<Roles> actualAllRoles = this.rolesServiceImpl.getAllRoles();
        assertSame(rolesList, actualAllRoles);
        assertTrue(actualAllRoles.isEmpty());
        verify(this.rolesRepository).findAll();
    }

}

