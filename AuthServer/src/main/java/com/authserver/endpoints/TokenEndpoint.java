package com.authserver.endpoints;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.authserver.dto.clientAuthDTO;
import com.authserver.dto.clientDTO;
import com.authserver.dto.userDTO;
import com.authserver.objects.AuthCode;
import com.authserver.objects.Client;
import com.authserver.objects.User;
import com.authserver.repositories.CrudAuthCodes;
import com.authserver.repositories.CrudRepositoryClients;
import com.authserver.repositories.CrudRepositoryUsers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;


@RestController
@RequestMapping("/token")
public class TokenEndpoint {


    @Autowired
    CrudRepositoryClients crudClients;

    @Autowired
    CrudAuthCodes crudCodes;

    private SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<?> generateToken(@RequestBody clientAuthDTO authDTO) {

    String code = authDTO.code;
    String clientId = authDTO.clientId;
    String clientSecret = authDTO.clientSecret;
    String clientURL = authDTO.clientURL;


    Optional<Client> optionalClient = crudClients.findById(clientId);
    if(optionalClient.isEmpty()){
        return ResponseEntity.badRequest().body("Client non-existent!");
    }

    Client currentClient = optionalClient.get();

    /* 
    if(!currentClient.getClientScrect().equals(clientSecret)){
        return ResponseEntity.badRequest().body("Client secret wrong !");
    }
    */

    //Hashed Secret
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    if(!encoder.matches(clientSecret, currentClient.getClientScrect())){
        return ResponseEntity.badRequest().body("Client secret wrong !");
    }


    if(!currentClient.getClientURL().equals(clientURL)){
        return ResponseEntity.badRequest().body("Client URL wrong !");
    }

    Optional<AuthCode> authCode = crudCodes.findById(code);
    if(authCode.isEmpty()){
        return ResponseEntity.badRequest().body("Code not valid !");
    }

    AuthCode tokenCode = authCode.get();

        String token = Jwts.builder()
                .setSubject(tokenCode.getUserId())
                .setIssuer("authServer")
                .claim("clientId", tokenCode.getClientId())
                .claim("issuedBy", "p&p")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(3600)))
                .signWith(secretKey, SignatureAlgorithm.HS256) // Use the secure key here
                .compact();
    

    Map<String, String> accessToken = new HashMap<>();
    accessToken.put("accessToken", token);

    return ResponseEntity.ok(accessToken);

    }

}
