package com.fileshare.model;


import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="Item", schema = "FileShare.dbo")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ItemId")
    private Integer itemId;

    @Column(name = "ItemName")
    private String itemName;

    @Column(name = "ItemDescription")
    private String itemDescription;

    @Column(name = "IsSharable")
    private Boolean isSharable;

//    @Column(name = "Owner")
//    private Integer ownerId;

//    @Column(name = "SharedBy")
//    private Integer sharedByUserId;

    @JsonProperty
//    @OneToOne(mappedBy="ownerUserEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
////    @JsonManagedReference(value = "seller-prorated3")

    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE,CascadeType.DETACH})
    @JoinColumn(name="Owner", unique=true, nullable=false)
    //@JoinTable(name = "FileShare.dbo.UserItem", joinColumns = { @JoinColumn(name = "UserId")  })
    private UserEntity userOwned;


    @JsonProperty
//    @OneToOne(mappedBy="ownerUserEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
////    @JsonManagedReference(value = "seller-prorated3")

    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE,CascadeType.DETACH})
    @JoinColumn(name="SharedBy", unique=true)
    //@JoinTable(name = "FileShare.dbo.UserItem", joinColumns = { @JoinColumn(name = "UserId")  })
    private UserEntity sharedBy;

    public UserEntity getSharedBy() {
        return sharedBy;
    }

    public void setSharedBy(UserEntity sharedBy) {
        this.sharedBy = sharedBy;
    }

    public UserEntity getUserOwned() {
        return userOwned;
    }

    public void setUserOwned(UserEntity userOwned) {
        this.userOwned = userOwned;
    }




//    public Integer getSharedByUserId() {
//        return sharedByUserId;
//    }
//
//    public void setSharedByUserId(Integer sharedByUserId) {
//        this.sharedByUserId = sharedByUserId;
//    }



    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Boolean getSharable() {
        return isSharable;
    }

    public void setSharable(Boolean sharable) {
        isSharable = sharable;
    }

//    public Integer getOwnerId() {
//        return ownerId;
//    }
//
//    public void setOwnerId(Integer ownerId) {
//        this.ownerId = ownerId;
//    }
}
