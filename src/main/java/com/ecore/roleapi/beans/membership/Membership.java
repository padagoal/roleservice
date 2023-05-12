package com.ecore.roleapi.beans.membership;

import com.ecore.roleapi.beans.roles.Roles;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "membership")
public class Membership {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "userid")
    private String userId;

    @Column(name = "teamid")
    private String teamId;

    @ManyToOne
    @JoinColumn(name = "rolid")
    private Roles role;

    @PrePersist
    private void setID(){
        this.id = UUID.randomUUID().toString();
    }
}
