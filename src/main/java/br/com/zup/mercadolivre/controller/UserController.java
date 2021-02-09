package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.UserRequestDto;
import br.com.zup.mercadolivre.dto.UserDto;
import br.com.zup.mercadolivre.handler.ObjectHandler;
import br.com.zup.mercadolivre.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController extends ObjectHandler {

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UserRequestDto userRequestDto) {
        User response = userRepository.save(userRequestDto.toModel(encoder));
        return ResponseEntity.ok().body(new UserDto(response));
    }
}
