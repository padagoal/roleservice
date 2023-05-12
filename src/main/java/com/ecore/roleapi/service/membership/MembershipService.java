package com.ecore.roleapi.service.membership;

import com.ecore.roleapi.beans.membership.Membership;
import com.ecore.roleapi.beans.membership.MembershipRequestDTO;
import com.ecore.roleapi.beans.membership.MembershipResponseDTO;
import com.ecore.roleapi.exception.ResourceNotFoundException;

import java.util.List;

public interface MembershipService {

    MembershipResponseDTO assignRole(MembershipRequestDTO membershipRequestDTO) throws ResourceNotFoundException;

    List<Membership> getAllMembershipByRole(String id);

    Membership getRoleByMembership(String teamId, String userId);

}
