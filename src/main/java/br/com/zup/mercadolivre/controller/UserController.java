package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.UserRequestDto;
import br.com.zup.mercadolivre.dto.UserDto;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UserRequestDto userRequestDto) {
        User response = repository.save(userRequestDto.toModel(encoder));
        return ResponseEntity.ok().body(new UserDto(response));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(repository.findAll());
    }
}
