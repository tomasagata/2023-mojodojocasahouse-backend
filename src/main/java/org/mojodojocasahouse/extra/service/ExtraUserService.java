package org.mojodojocasahouse.extra.service;

import org.mojodojocasahouse.extra.dto.ExtraUserLoginDto;
import org.mojodojocasahouse.extra.dto.ExtraUserLoginResponseDto;
import org.mojodojocasahouse.extra.dto.ExtraUserRegistrationDto;
import org.mojodojocasahouse.extra.dto.ExtraUserRegistrationResponseDto;
//import org.mojodojocasahouse.extra.dto.UserRegistrationRequest;
//import org.mojodojocasahouse.extra.dto.UserRegistrationResponse;
//import org.mojodojocasahouse.extra.exception.MismatchingPasswordsException;


public interface ExtraUserService {

    ExtraUserRegistrationResponseDto registrarUsuario(ExtraUserRegistrationDto userRegistrationDto);

    ExtraUserLoginResponseDto loguearUsuario(ExtraUserLoginDto extraUserLoginDto);
//    UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationDto) throws MismatchingPasswordsException;
}
