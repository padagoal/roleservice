package com.ecore.roleapi.service.Roles;


import com.ecore.roleapi.annotation.Apilog;
import com.ecore.roleapi.beans.membership.Membership;
import com.ecore.roleapi.beans.membership.MembershipRequestDTO;
import com.ecore.roleapi.beans.membership.MembershipResponseDTO;
import com.ecore.roleapi.beans.roles.Roles;
import com.ecore.roleapi.beans.roles.RolesRequestDTO;
import com.ecore.roleapi.beans.roles.RolesResponseDTO;
import com.ecore.roleapi.service.membership.MembershipService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/roles")
@AllArgsConstructor
public class RolesAPI{

    private final RolesService rolesService;
    private final MembershipService membershipService;

    @Apilog
    @GetMapping("/")
    public ResponseEntity<List<Roles>> listRoles(){
        List<Roles> roles = rolesService.getAllRoles();
        return ResponseEntity.ok().body(roles);
    }

    @Apilog
    @PostMapping("/")
    public ResponseEntity<RolesResponseDTO> createRole(@RequestBody RolesRequestDTO rolesRequestDTO){

        return ResponseEntity.ok().body(rolesService.createRole(rolesRequestDTO));
    }

    @Apilog
    @PostMapping("/assign")
    public ResponseEntity<MembershipResponseDTO> assignRole(@RequestBody MembershipRequestDTO membershipRequestDTO) throws Exception {
        MembershipResponseDTO membershipResponseDTO = membershipService.assignRole(membershipRequestDTO);
        return ResponseEntity.ok().body(membershipResponseDTO);
    }

    @Apilog
    @GetMapping(value = "/{roleid}/memberships")
    public ResponseEntity<List<Membership>> listMemberShipsWithSpecificRole(@PathVariable(name = "roleid") String roleid ){
        return ResponseEntity.ok().body(membershipService.getAllMembershipByRole(roleid));
    }

    @Apilog
    @GetMapping(value = "/teams/{teamid}/users/{userid}")
    public ResponseEntity<Membership> getRoleByMembership(@PathVariable(name = "teamid") String teamid, @PathVariable(name = "userid")
            String userid){
        return ResponseEntity.ok().body(membershipService.getRoleByMembership(teamid,userid));
    }
}
