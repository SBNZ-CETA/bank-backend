package bank.controller;

import dtos.ExampleDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping(value = "/hello", produces = "application/json")
    public ResponseEntity<ExampleDto> hello() {
        return new ResponseEntity<>(new ExampleDto("Hello World!"), HttpStatus.OK);
    }
}
