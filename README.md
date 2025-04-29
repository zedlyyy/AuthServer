# AuthServer for the Software Security class.
By Pedro Galego (72298) and Pedro Mascarenhas (63240)


# How to deploy

## Authorization Server

1. IntelIJ
   Select the automatically created configuration and click run
2. Terminal
   cd AuthServer
   ./mvnw spring-boot:run

## Client Application

1. npm install http-server
2. cd ClientApplication
3. http-server -c-1


# How to test

## Using POSTMAN or Curl
1. Create client
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
   
