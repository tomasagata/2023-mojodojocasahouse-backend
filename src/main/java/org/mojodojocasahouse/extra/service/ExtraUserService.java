package org.mojodojocasahouse.extra.service;

import org.mojodojocasahouse.extra.dto.ExtraUserLoginDto;
import org.mojodojocasahouse.extra.dto.ExtraUserLoginResponseDto;
import org.mojodojocasahouse.extra.dto.ExtraUserRegistrationDto;
import org.mojodojocasahouse.extra.dto.ExtraUserRegistrationResponseDto;


public interface ExtraUserService {

    ExtraUserRegistrationResponseDto registrarUsuario(ExtraUserRegistrationDto userRegistrationDto);

    ExtraUserLoginResponseDto loguearUsuario(ExtraUserLoginDto extraUserLoginDto);
}
