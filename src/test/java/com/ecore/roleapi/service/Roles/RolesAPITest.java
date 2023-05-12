package com.ecore.roleapi.service.Roles;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.ecore.roleapi.beans.membership.Membership;
import com.ecore.roleapi.beans.membership.MembershipRequestDTO;
import com.ecore.roleapi.beans.membership.MembershipResponseDTO;
import com.ecore.roleapi.beans.roles.Roles;
import com.ecore.roleapi.beans.roles.RolesRequestDTO;
import com.ecore.roleapi.beans.roles.RolesResponseDTO;
import com.ecore.roleapi.service.membership.MembershipService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {RolesAPI.class})
@ExtendWith(SpringExtension.class)
class RolesAPITest {
    @MockBean
    private MembershipService membershipService;

    @Autowired
    private RolesAPI rolesAPI;

    @MockBean
    private RolesService rolesService;

    @Test
    void testCreateRole() throws Exception {
        when(this.rolesService.createRole((RolesRequestDTO) any())).thenReturn(new RolesResponseDTO("ae42-aa11", "TEST ROLE"));

        RolesRequestDTO rolesRequestDTO = new RolesRequestDTO();
        rolesRequestDTO.setName("TEST ROLE");
        String content = (new ObjectMapper()).writeValueAsString(rolesRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/roles/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.rolesAPI)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":\"ae42-aa11\",\"name\":\"TEST ROLE\"}"));
    }


    @Test
    void testAssignRole() throws Exception {
        when(this.membershipService.assignRole((MembershipRequestDTO) any())).thenReturn(
                new MembershipResponseDTO("aa11", "aa11-ee22", "fe90-ac12",
                        new RolesResponseDTO("ae42-aa11", "TEST ROLE")));

        MembershipRequestDTO membershipRequestDTO = new MembershipRequestDTO();
        membershipRequestDTO.setUserId("aa11-ee22");
        membershipRequestDTO.setTeamId("fe90-ac12");
        membershipRequestDTO.setRolId("ae42-aa11");
        String content = (new ObjectMapper()).writeValueAsString(membershipRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/roles/assign")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(this.rolesAPI)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"id\":\"aa11\",\"userid\":\"aa11-ee22\",\"teamId\":\"fe90-ac12\",\"rolId\":{\"id\":\"ae42-aa11\",\"name\":\"TEST ROLE\"}}"));
    }



    @Test
    void testGetRoleByMembership() throws Exception {
        Roles roles = new Roles();
        roles.setIsDefault(true);
        roles.setId("ae42-aa11");
        roles.setName("TEST ROLE");

        Membership membership = new Membership();
        membership.setUserId("aa44-bb99");
        membership.setId("aa11-aa11-aa11");
        membership.setTeamId("bc22-bc22-bc22");
        membership.setRole(roles);
        when(this.membershipService.getRoleByMembership((String) any(), (String) any())).thenReturn(membership);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/roles/teams/{teamid}/users/{userid}",
                "Teamid", "Userid");
        MockMvcBuilders.standaloneSetup(this.rolesAPI)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":\"aa11-aa11-aa11\",\"userId\":\"aa44-bb99\",\"teamId\":\"bc22-bc22-bc22\",\"role\":{\"id\":\"ae42-aa11\",\"name\":\"TEST ROLE\",\"isDefault\":true}}"));
    }


    @Test
    void testListMemberShipsWithSpecificRole() throws Exception {
        when(this.membershipService.getAllMembershipByRole((String) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/roles/{roleid}/memberships", "Roleid");
        MockMvcBuilders.standaloneSetup(this.rolesAPI)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testListRoles() throws Exception {
        when(this.rolesService.getAllRoles()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/roles/");
        MockMvcBuilders.standaloneSetup(this.rolesAPI)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


}

