package bank.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bank.security.domain.RegisterDto;
import bank.service.UserService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(path="/users")
public class UserController {
    
    private UserService userService;


    @PostMapping(path="/register")
    public boolean register(@RequestBody RegisterDto request){
        return userService.register(request);
    }

}
