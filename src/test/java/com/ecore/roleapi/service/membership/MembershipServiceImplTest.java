package com.ecore.roleapi.service.membership;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ecore.roleapi.beans.membership.Membership;
import com.ecore.roleapi.beans.membership.MembershipRequestDTO;
import com.ecore.roleapi.beans.membership.MembershipResponseDTO;
import com.ecore.roleapi.beans.roles.Roles;
import com.ecore.roleapi.beans.roles.RolesResponseDTO;
import com.ecore.roleapi.beans.users.Users;
import com.ecore.roleapi.repositories.MembershipRepository;
import com.ecore.roleapi.service.Roles.RolesService;
import com.ecore.roleapi.service.teams.TeamsService;
import com.ecore.roleapi.service.users.UsersService;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MembershipServiceImpl.class})
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class MembershipServiceImplTest {
    @MockBean
    private MembershipRepository membershipRepository;

    @Autowired
    private MembershipServiceImpl membershipServiceImpl;

    @MockBean
    private RolesService rolesService;

    @MockBean
    private TeamsService teamsService;

    @MockBean
    private UsersService usersService;

    @Test
    void testAssignRole() throws Exception {
        Users users = new Users();
        users.setLastName("Doe");
        users.setLocation("Florida");
        users.setId("aa44-bb99");
        users.setDisplayName("Display Name");
        users.setFirstName("Jane");
        users.setAvatarUrl("https://example.org/example");
        Optional<Users> ofResult = Optional.of(users);
        when(this.usersService.getUser((String) any())).thenReturn(ofResult);
        when(this.teamsService.isUserInTeam((String) any(), (String) any())).thenReturn(true);

        Roles roles = new Roles();
        roles.setIsDefault(true);
        roles.setId("ae12-ea21");
        roles.setName("Test Role");
        when(this.rolesService.searchRole((String) any())).thenReturn(roles);

        Roles roles1 = new Roles();
        roles1.setIsDefault(false);
        roles1.setId("cb34-bc43");
        roles1.setName("Second Test Role");

        Membership membership = new Membership();
        membership.setUserId("aa44-bb99");
        membership.setId("aa11-aa11-aa11");
        membership.setTeamId("bc22-bc22-bc22");
        membership.setRole(roles1);

        Roles roles2 = new Roles();
        roles2.setIsDefault(false);
        roles2.setId("cd13-dc31");
        roles2.setName("Third Test Role");

        Membership membership1 = new Membership();
        membership1.setUserId("aa44-bb99");
        membership1.setId("bb11-bb22-bb33");
        membership1.setTeamId("bc22-bc22-bc22");
        membership1.setRole(roles2);
        when(this.membershipRepository.save((Membership) any())).thenReturn(membership1);
        when(this.membershipRepository.findByTeamIdAndUserId((String) any(), (String) any())).thenReturn(membership);
        MembershipResponseDTO actualAssignRoleResult = this.membershipServiceImpl
                .assignRole(new MembershipRequestDTO("aa44-bb99", "bc22-bc22-bc22", "ae12-ea21"));
        assertEquals("bb11-bb22-bb33", actualAssignRoleResult.getId());
        assertEquals("aa44-bb99", actualAssignRoleResult.getUserId());
        assertEquals("bc22-bc22-bc22", actualAssignRoleResult.getTeamId());
        RolesResponseDTO rolId = actualAssignRoleResult.getRolId();
        assertEquals("cd13-dc31", rolId.getId());
        assertEquals("Third Test Role", rolId.getName());
        verify(this.usersService).getUser((String) any());
        verify(this.teamsService).isUserInTeam((String) any(), (String) any());
        verify(this.rolesService).searchRole((String) any());
        verify(this.membershipRepository).findByTeamIdAndUserId((String) any(), (String) any());
        verify(this.membershipRepository).save((Membership) any());
    }


    @Test
    void testGetAllMembershipByRole() {
        ArrayList<Membership> membershipList = new ArrayList<>();
        when(this.membershipRepository.findByRole_Id((String) any())).thenReturn(membershipList);
        List<Membership> actualAllMembershipByRole = this.membershipServiceImpl.getAllMembershipByRole("aa44-bb99");
        assertSame(membershipList, actualAllMembershipByRole);
        assertTrue(actualAllMembershipByRole.isEmpty());
        verify(this.membershipRepository).findByRole_Id((String) any());
    }

    @Test
    void testGetRoleByMembership() {
        Roles roles = new Roles();
        roles.setIsDefault(true);
        roles.setId("ae12-ea21");
        roles.setName("Test Role");

        Membership membership = new Membership();
        membership.setUserId("aa44-bb99");
        membership.setId("aa11-aa11-aa11");
        membership.setTeamId("bc22-bc22-bc22");
        membership.setRole(roles);
        when(this.membershipRepository.findByTeamIdAndUserId((String) any(), (String) any())).thenReturn(membership);
        assertSame(membership, this.membershipServiceImpl.getRoleByMembership("bc22-bc22-bc22", "aa44-bb99"));
        verify(this.membershipRepository).findByTeamIdAndUserId((String) any(), (String) any());
    }
}

