package com.authserver.endpoints;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.authserver.dto.clientDTO;
import com.authserver.dto.userDTO;
import com.authserver.objects.AuthCode;
import com.authserver.objects.Client;
import com.authserver.objects.User;
import com.authserver.repositories.CrudAuthCodes;
import com.authserver.repositories.CrudRepositoryClients;
import com.authserver.repositories.CrudRepositoryUsers;


@RestController
@RequestMapping("/authorize")
public class AuthorizationEndpoint {

    @Autowired
    CrudRepositoryClients crudClients;

    @Autowired
    CrudRepositoryUsers crudUsers;

    @Autowired
    CrudAuthCodes crudCodes;

    @GetMapping
    public ResponseEntity<?> authorize(@RequestParam String responseType, String redirectURL, String scope, String state, String clientID) {
        
        Optional<Client> optionalClient = crudClients.findById(clientID);
        
        if(optionalClient.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid clientId !");
        }
        
        /*TODO 
            Lançar HTML page para receber username e pass do user
            Depois fazer um POST para /authorize/confirm para confirmar a identidade do user e posteriormente lançar e guardar o authCode para o client
            Este POST tem que conter o username, password, clientId, redirectURL, state
        */

        return null;

    }
    
    @PostMapping("/confirm")
    public ResponseEntity<?> postMethodName(@RequestParam String userId, String password, String clientId, String redirectURL, String state) {
       
        Optional<User> userOpt = crudUsers.findById(userId);

        if(userOpt.get().getUserPass() != password || userOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String code = UUID.randomUUID().toString();

        AuthCode authCode = new AuthCode(userId,clientId,code);

        crudCodes.save(authCode);

        
        URI redirect = URI.create(redirectURL + "?code=" + code + "&state=" + state);
        /*TODO
            Redirecionar o user para o url acima 
        */
        return null;
    }
    
    


}
