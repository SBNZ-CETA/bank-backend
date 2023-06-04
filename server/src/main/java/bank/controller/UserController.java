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
        System.out.println(request.getName());
        System.out.println(request.getSurname());
        System.out.println(request.getEmail());
        return userService.register(request);
    }

}
