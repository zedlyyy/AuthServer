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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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


        String html = "<html><head>" +
                "<style>" +
                "body {" +
                "    margin: 0;" +
                "    padding: 0;" +
                "    height: 100vh;" +
                "    display: flex;" +
                "    justify-content: center;" +
                "    align-items: center;" +
                "    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;" +
                "    background: linear-gradient(135deg, #6a11cb, #2575fc);" +
                "}" +
                ".login-container {" +
                "    background: white;" +
                "    padding: 40px;" +
                "    border-radius: 12px;" +
                "    box-shadow: 0px 8px 24px rgba(0, 0, 0, 0.2);" +
                "    text-align: center;" +
                "    width: 300px;" +
                "}" +
                ".login-container h2 {" +
                "    margin-bottom: 20px;" +
                "    color: #333;" +
                "}" +
                ".login-container input[type='text'], " +
                ".login-container input[type='password'] {" +
                "    width: 100%;" +
                "    padding: 10px;" +
                "    margin: 10px 0;" +
                "    border: 1px solid #ccc;" +
                "    border-radius: 8px;" +
                "}" +
                ".login-container input[type='submit'] {" +
                "    width: 100%;" +
                "    padding: 12px;" +
                "    background-color: #6a11cb;" +
                "    border: none;" +
                "    color: white;" +
                "    font-weight: bold;" +
                "    border-radius: 8px;" +
                "    cursor: pointer;" +
                "    transition: background-color 0.3s ease;" +
                "}" +
                ".login-container input[type='submit']:hover {" +
                "    background-color: #2575fc;" +
                "}" +
                "</style>" +
                "</head><body>" +
                "<div class='login-container'>" +
                "<h2>Auth Server Login</h2>" +
                "<form method='POST' action='/authorize/confirm'>" +
                "Username:<br/><input type='text' name='userId'/><br/>" +
                "Password:<br/><input type='password' name='password'/><br/>" +
                "<input type='hidden' name='clientId' value='" + clientID + "'/>" +
                "<input type='hidden' name='redirectURL' value='" + redirectURL + "'/>" +
                "<input type='hidden' name='state' value='" + state + "'/>" +
                "<input type='submit' value='Login'/>" +
                "</form>" +
                "</div>" +
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

        //Hashed Password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        /*
        if(!Objects.equals(userOpt.get().getUserPass(), password)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        */

        if(!encoder.matches(password, userOpt.get().getUserPass())){
            System.out.println(userOpt.get().getUserPass() + "user pass");
            System.out.println(password + "asdasd");
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
