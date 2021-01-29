package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.config.security.CryptoConverter;
import br.com.zup.mercadolivre.config.security.CryptoConverterInterface;
import br.com.zup.mercadolivre.controller.request.UserRequestDto;
import br.com.zup.mercadolivre.dto.UserDto;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private CryptoConverter crypto;


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
