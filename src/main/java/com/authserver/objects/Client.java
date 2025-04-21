package com.authserver.objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Client {

    @Id
    private String clientId;

    private String clientURL;

    
    public Client (String clientId, String clientURL){
        this.clientId = clientId;
        this.clientURL = clientURL;
    }

    public void setClientId(String newClientId){
        this.clientId = newClientId;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientURL (String newClientURL) {
        this.clientURL = newClientURL;
    }

    public String getClientURL () {
        return this.clientURL;
    }
}
