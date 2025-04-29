package com.authserver.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

        //Hashed Secret
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedSecret = encoder.encode(clientSecret);
            
        Client client = new Client(clientId, hashedSecret, clientURL);
        crudClients.save(client);

        return ResponseEntity.ok("Client registered");
    }

    @PostMapping("/user")
    public ResponseEntity<String> registerClient(@RequestBody userDTO userDTO) {
        String userId = userDTO.userId;
        String userPass = userDTO.userPass;

        //Hashed Password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPass = encoder.encode(userPass);

        User user = new User(userId, hashedPass);
        crudUsers.save(user);

        return ResponseEntity.ok("User registered");
    }

    






}
