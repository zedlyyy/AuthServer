package com.authserver.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authserver.dto.clientDTO;
import com.authserver.dto.userDTO;
import com.authserver.objects.Client;
import com.authserver.objects.User;
import com.authserver.repositories.CrudRepositoryClients;
import com.authserver.repositories.CrudRepositoryUsers;

@RestController
@RequestMapping("/register")
public class RegistrationEndpoint {

    @Autowired
    CrudRepositoryClients crudClients;

    @Autowired
    CrudRepositoryUsers crudUsers;


    @PostMapping("/client")
    public ResponseEntity<String> registerClient(@RequestBody clientDTO clientDTO) {
    String clientId = clientDTO.clientId;
    String clientSecret = clientDTO.clientSecret;
    String clientURL = clientDTO.clientURL;

        
    Client client = new Client(clientId, clientSecret, clientURL);
    crudClients.save(client);

    return ResponseEntity.ok("Client registered");

    }

    @PostMapping("/user")
    public ResponseEntity<String> registerClient(@RequestBody userDTO userDTO) {
    String userId = userDTO.userId;
    String userPass = userDTO.userPass;

   //Podemos dar hash na pass depois

    User user = new User(userId, userPass);
    crudUsers.save(user);

    return ResponseEntity.ok("User registered");
    
    }






}
