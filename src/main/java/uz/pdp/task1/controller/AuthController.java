package uz.pdp.task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.task1.payload.LoginDto;
import uz.pdp.task1.security.GenerateToken;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    GenerateToken generateToken;
    @PostMapping("/login")
    public HttpEntity<?> Login(@RequestBody LoginDto loginDto) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUserName(),
                    loginDto.getPassword()
            ));
            String token = generateToken.generateToken(loginDto.getUserName());

            return ResponseEntity.ok(token);
        }
        catch (BadCredentialsException badCredentialsException){
            return ResponseEntity.status(401).body("User not found");
        }

    }

}
