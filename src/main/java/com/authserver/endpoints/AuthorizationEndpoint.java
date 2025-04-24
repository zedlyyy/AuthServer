package com.authserver.endpoints;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> authorize(@RequestParam String responseType,@RequestParam String redirectURL,@RequestParam String scope,@RequestParam String state,@RequestParam String clientID) {
        
        Optional<Client> optionalClient = crudClients.findById(clientID);
        
        if(optionalClient.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid clientId !");
        }
        
        /*TODO 
            Lançar HTML page para receber username e pass do user
            Depois fazer um POST para /authorize/confirm para confirmar a identidade do user e posteriormente lançar e guardar o authCode para o client
            Este POST tem que conter o username, password, clientId, redirectURL, state
        */


        String html = "<html><body>" +
                "<h2>Auth Server Login</h2>" +
                "<form method='POST' action='/authorize/confirm'>" +
                "Username: <input type='text' name='userId'/><br/>" +
                "Password: <input type='password' name='password'/><br/>" +
                "<input type='hidden' name='clientId' value='" + clientID + "'/>" +
                "<input type='hidden' name='redirectURL' value='" + redirectURL + "'/>" +
                "<input type='hidden' name='state' value='" + state + "'/>" +
                "<input type='submit' value='Login'/>" +
                "</form>" +
                "</body></html>";

        return ResponseEntity.ok().body(html);

        //return ResponseEntity.ok("okay");

    }
    
    @PostMapping("/confirm")
    public ResponseEntity<?> postMethodName(@RequestParam String userId,@RequestParam String password,@RequestParam String clientId,@RequestParam String redirectURL,@RequestParam String state) {
       
        Optional<User> userOpt = crudUsers.findById(userId);

        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("UserId:" + userId + " not found");
        }

        if(!Objects.equals(userOpt.get().getUserPass(), password)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String code = UUID.randomUUID().toString();

        AuthCode authCode = new AuthCode(userId,clientId,code);

        crudCodes.save(authCode);

        
        URI redirect = URI.create(redirectURL + "?code=" + code + "&state=" + state);
        /*TODO
            Redirecionar o user para o url acima 
        */

        return ResponseEntity.status(HttpStatus.FOUND).location(redirect).build();

        //return null;
    }
    
    


}
