package bank.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bank.security.domain.JwtResponse;
import bank.security.domain.LoginDto;
import bank.service.UserService;
import facts.User;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    @PostMapping
    public ResponseEntity<JwtResponse> generateToken(@RequestBody LoginDto authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e) {
            throw new NotFoundException("Invalid username or password");
        }
        User user = userService.getByUsername(authRequest.getUsername());
        String jwt = jwtUtil.generateToken(authRequest.getUsername(), user.getType().toString() , user.getId());
        return new ResponseEntity<>(new JwtResponse(jwt),HttpStatus.OK);
    }
}
