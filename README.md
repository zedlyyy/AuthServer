# AuthServer for the Software Security class.
By Pedro Galego (72298) and Pedro Mascarenhas (63240)


# How to deploy

## Authorization Server

### Locally
1. IntelIJ
   Select the automatically created configuration and click run
2. Terminal
   cd AuthServer
   ./mvnw spring-boot:run
3. Docker
   Open Docker
   docker build -t authserver .
   docker run -p 8080:8080 authserver

### Deployed
AuthServerURL: https://authserver-9q8t.onrender.com

## Client Application

1. npm install http-server
2. cd ClientApplication
3. http-server -c-1


# How to test

## Using POSTMAN or Curl
1. Create client **(do not change the clientId and use the correct clientURL that is given from running http-server)**

POST
{authServerURL}/register/client

body:  
{  
  "clientId": "client1",  
  "clientSecret": "password",  
  "clientURL": {clientURL}  
}  

2. Create user
POST  
{authServerURL}/register/user  

body:  
{  
  "userId": "user1",  
  "userPass": "password"  
}  

3. open on browser {clientURL}/login and proceed with Oauth process.
   
