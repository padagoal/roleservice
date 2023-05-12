package com.ecore.roleapi.service.membership;

import com.ecore.roleapi.beans.membership.Membership;
import com.ecore.roleapi.beans.membership.MembershipRequestDTO;
import com.ecore.roleapi.beans.membership.MembershipResponseDTO;
import com.ecore.roleapi.beans.roles.Roles;
import com.ecore.roleapi.beans.roles.RolesResponseDTO;
import com.ecore.roleapi.exception.ResourceNotFoundException;
import com.ecore.roleapi.repositories.MembershipRepository;
import com.ecore.roleapi.service.Roles.RolesService;
import com.ecore.roleapi.service.teams.TeamsService;
import com.ecore.roleapi.service.users.UsersService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class MembershipServiceImpl implements MembershipService {

    private final RolesService rolesService;
    private final TeamsService teamsService;
    private final UsersService usersService;
    private final MembershipRepository membershipRepository;

    @Override
    public MembershipResponseDTO assignRole(MembershipRequestDTO membershipRequestDTO) throws ResourceNotFoundException {
        Membership membership = new Membership();
        MembershipResponseDTO membershipResponseDTO = null;
        //Check if the user exists
        usersService.getUser(membershipRequestDTO.getUserId());

        //Check if the user is in the team
        teamsService.isUserInTeam(membershipRequestDTO.getTeamId(),membershipRequestDTO.getUserId());

        membership.setTeamId(membershipRequestDTO.getTeamId());
        membership.setUserId(membershipRequestDTO.getUserId());
        Roles rolForTeamMember=null;

        try{
            if(membershipRequestDTO.getRolId() == null ||
                    Objects.requireNonNull(membershipRequestDTO.getRolId()).equalsIgnoreCase("")) {
                rolForTeamMember = rolesService.searchDefaultRole();
            }else{
                rolForTeamMember = searchRole(membershipRequestDTO.getRolId());
            }

        }catch (ResourceNotFoundException ex){
            throw new ResourceNotFoundException(String.format("The given roleId [%s] was not found",membershipRequestDTO.getRolId()));
        }


        membership.setRole(rolForTeamMember);

        log.info("Rol info {} {} {}",rolForTeamMember.getId(),rolForTeamMember.getName(),
                rolForTeamMember.getIsDefault());
        try{
            //Here we search if the user has a role in the team, if not will create the membership
            //Or update with the new rol
            Membership membershipAux = getRoleByMembership(membershipRequestDTO.getTeamId(),membershipRequestDTO.getUserId());
            if(membershipAux!= null && membershipAux.getId()!= null){
                //With this it's prevent to create more than one role for a same user in a same team
                membership.setId(membershipAux.getId());
            }
            membership = membershipRepository.save(membership);
            log.info("New Membership Added: userId:[{}] - teamID: [{}] - rolID: [{}]",membership.getId(),
                    membership.getUserId(),membership.getTeamId());
            membershipResponseDTO = buildResponseDTO(membership);
        }catch (Exception e){
            log.error("Exception occur trying to save a new Membership ",e);
        }
        return membershipResponseDTO;
    }


    private Roles searchRole(String id){
        Roles rol = null;
        try{
            rol = rolesService.searchRole(id);
        }catch (ResourceNotFoundException ex){
            log.warn("The given ID for Roles doesn't exists - ID:[{}]", id);
            throw new ResourceNotFoundException(String.format("The given ID for Roles doesn't exists - ID:[%s]", id));
        }catch (Exception e){
            log.error("An error occur when trying to retrieve a rol from database - ID: {}",id,e);
        }
        return rol;
    }

    private MembershipResponseDTO buildResponseDTO(Membership membership){
        MembershipResponseDTO membershipResponseDTO = new MembershipResponseDTO();
        membershipResponseDTO.setId(membership.getId());
        membershipResponseDTO.setTeamId(membership.getTeamId());
        membershipResponseDTO.setUserId(membership.getUserId());
        membershipResponseDTO.setRolId(new RolesResponseDTO(membership.getRole().getId(),membership.getRole().getName()));

        return membershipResponseDTO;
    }

    @Override
    public List<Membership> getAllMembershipByRole(String id) {

        List<Membership> membershipList = membershipRepository.findByRole_Id(id);

        return membershipList;
    }

    @Override
    public Membership getRoleByMembership(String teamId, String userId) {
        Membership membership = membershipRepository.findByTeamIdAndUserId(teamId,userId);
        return membership;
    }

}
