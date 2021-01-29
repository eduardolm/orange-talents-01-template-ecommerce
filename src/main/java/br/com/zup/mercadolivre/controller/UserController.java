package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.config.security.CryptoProperties;
import br.com.zup.mercadolivre.controller.request.UserRequestDto;
import br.com.zup.mercadolivre.dto.UserDto;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private CryptoProperties crypto;


    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UserRequestDto userRequestDto) {
        User response = repository.save(userRequestDto.toModel(crypto));
        return ResponseEntity.ok().body(new UserDto(response));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(repository.findAll());
    }
}
