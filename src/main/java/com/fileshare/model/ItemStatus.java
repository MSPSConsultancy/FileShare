package com.fileshare.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class ItemStatus {

    private Integer itemId;

    private String itemName;

    private String itemDescription;


    private Boolean isSharable;


    private Integer ownerId;

    private String ownerName;

    private Integer sharedByUserId;


    private String sharedByUserName;

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

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Integer getSharedByUserId() {
        return sharedByUserId;
    }

    public void setSharedByUserId(Integer sharedByUserId) {
        this.sharedByUserId = sharedByUserId;
    }

    public String getSharedByUserName() {
        return sharedByUserName;
    }

    public void setSharedByUserName(String sharedByUserName) {
        this.sharedByUserName = sharedByUserName;
    }
}
