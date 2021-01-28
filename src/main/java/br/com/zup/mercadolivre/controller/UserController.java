package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.UseRequestDto;
import br.com.zup.mercadolivre.dto.UserDto;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UseRequestDto useRequestDto) {
        User response = repository.save(useRequestDto.toModel());
        return ResponseEntity.ok().body(new UserDto(response));
    }
}
