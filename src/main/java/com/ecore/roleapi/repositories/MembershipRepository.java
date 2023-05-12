package com.ecore.roleapi.repositories;

import com.ecore.roleapi.beans.membership.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipRepository extends JpaRepository<Membership,String> {
    List<Membership> findByRole_Id(String id);
    Membership findByTeamIdAndUserId(String teamId,String userId);
}
