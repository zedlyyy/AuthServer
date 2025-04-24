package com.authserver.objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Client {

    @Id
    private String clientId;


    private String clientSecret;
    private String clientURL;

    public Client() {
    }
    
    public Client (String clientId,String clientSecret, String clientURL){
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.clientURL = clientURL;
    }

    public void setClientId(String newClientId){
        this.clientId = newClientId;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientSecret(String newClientSecret){
        this.clientSecret = newClientSecret;
    }

    public String getClientScrect(){
        return this.clientSecret;
    }

    public void setClientURL (String newClientURL) {
        this.clientURL = newClientURL;
    }

    public String getClientURL () {
        return this.clientURL;
    }
}
