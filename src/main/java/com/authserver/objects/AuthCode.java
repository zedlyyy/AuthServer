package com.authserver.objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AuthCode {

    @Id
    private String authCode;
    
    private String userId;
    private String clientId;


    public AuthCode(String userId, String clientId, String authCode){
        this.userId = userId;
        this.clientId = clientId;
        this.authCode = authCode;
    }

    public void setUserId(String newUserId) {
        this.userId = newUserId;
    }

    public String getUserId() {
        return userId;
    }

    public void setClientId(String newClientId) {
        this.clientId = newClientId;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setAuthCode(String newAuthCode) {
        this.authCode = newAuthCode;
    }

    public String getAuthCode() {
        return authCode;
    }



}
