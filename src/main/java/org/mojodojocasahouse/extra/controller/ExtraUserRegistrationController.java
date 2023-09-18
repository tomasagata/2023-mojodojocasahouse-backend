package org.mojodojocasahouse.extra.controller;

import jakarta.validation.Valid;
import org.mojodojocasahouse.extra.dto.ExtraUserRegistrationResponseDto;
import org.mojodojocasahouse.extra.service.ExtraUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.mojodojocasahouse.extra.dto.ExtraUserRegistrationDto;

@Controller
public class ExtraUserRegistrationController {

    private final ExtraUserService userService;
    public ExtraUserRegistrationController(ExtraUserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ExtraUserRegistrationResponseDto> registerUserAccount(@Valid @RequestBody ExtraUserRegistrationDto extraUserRegistrationDto) {
        return new ResponseEntity<>(
                userService.registrarUsuario(extraUserRegistrationDto),
                HttpStatus.CREATED
        );
    }
}
