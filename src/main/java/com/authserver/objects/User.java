package com.authserver.objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    private String userId;
    
    private String userPass;

    public User (String useString, String userPass){
        this.userId = userId;
        this.userPass = userPass;
    }

    public void setUserId(String newUserId){
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserPass (String newUserPass) {
        this.userPass = userPass;
    }
    
    public String getUserPass () {
        return this.userPass;
    }
}
