package org.mojodojocasahouse.extra.service.impl;

import jakarta.validation.ConstraintViolationException;

import java.util.Optional;

import org.mojodojocasahouse.extra.dto.ExtraUserLoginDto;
import org.mojodojocasahouse.extra.dto.ExtraUserLoginResponseDto;
import org.mojodojocasahouse.extra.dto.ExtraUserRegistrationDto;
import org.mojodojocasahouse.extra.dto.ExtraUserRegistrationResponseDto;
import org.mojodojocasahouse.extra.model.impl.ExtraUser;
import org.mojodojocasahouse.extra.repository.ExtraUserRepository;
import org.mojodojocasahouse.extra.service.ExtraUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ExtraUserServiceImpl implements ExtraUserService {

    private final ExtraUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public ExtraUserServiceImpl(ExtraUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ExtraUserRegistrationResponseDto registrarUsuario(ExtraUserRegistrationDto userRegistrationDto) throws ConstraintViolationException {
        ExtraUser usuario = new ExtraUser(userRegistrationDto.getFirstName(),
            userRegistrationDto.getLastName(),
            userRegistrationDto.getEmail(),
            this.passwordEncoder.encode(userRegistrationDto.getPassword()));
        userRepository.save(usuario);
        return new ExtraUserRegistrationResponseDto("ExtraUser created successfully");
    }

    ExtraUserLoginDto extraUserLoginDto;
    public ExtraUserLoginResponseDto loguearUsuario(ExtraUserLoginDto extraUserLoginDto) {
        ExtraUser user1 = userRepository.findByEmail(extraUserLoginDto.getEmail());
        if (user1 != null) {
            String password = extraUserLoginDto.getPassword();
            String encodedPassword = user1.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<ExtraUser> user = userRepository.findOneByEmailAndPassword(extraUserLoginDto.getEmail(), encodedPassword);
                if (user.isPresent()) {
                    return new ExtraUserLoginResponseDto("Login Success", true);
                } else {
                    return new ExtraUserLoginResponseDto("Login Failed", false);
                }
            } else {
                return new ExtraUserLoginResponseDto("password Not Match", false);
            }
        }else {
            return new ExtraUserLoginResponseDto("Email not exits", false);
        }
    }
}
