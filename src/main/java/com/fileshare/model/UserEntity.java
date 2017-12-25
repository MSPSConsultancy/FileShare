package com.fileshare.model;


import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="UserItem", schema = "FileShare.dbo")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name = "UserId")
    private Integer userId;

    @Column(name = "UserName")
    private String userName;


//    @OneToOne(cascade=CascadeType.ALL)
//    @JoinColumn(name="ItemId")
//@OneToOne(optional=false, mappedBy="userOwned")
//    private ItemEntity itemEntity;


//    public ItemEntity getItemEntity() {
//        return itemEntity;
//    }
//
//    public void setItemEntity(ItemEntity itemEntity) {
//        this.itemEntity = itemEntity;
//    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
