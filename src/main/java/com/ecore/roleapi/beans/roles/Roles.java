package com.ecore.roleapi.beans.roles;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "isdefault")
    private Boolean isDefault;


    @PrePersist
    private void setID(){
        this.id = UUID.randomUUID().toString();
        this.isDefault = false;
    }
}


