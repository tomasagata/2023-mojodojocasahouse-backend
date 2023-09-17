package org.mojodojocasahouse.extra.service.impl;

import org.mojodojocasahouse.extra.dto.UserRegistrationRequest;
import org.mojodojocasahouse.extra.dto.UserRegistrationResponse;
import org.mojodojocasahouse.extra.exception.MismatchingPasswordsException;
import org.mojodojocasahouse.extra.model.impl.ExtraUser;
import org.mojodojocasahouse.extra.repository.ExtraUserRepository;
import org.mojodojocasahouse.extra.service.ExtraUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtraUserServiceImpl implements ExtraUserService {

    private final ExtraUserRepository userRepository;

    @Autowired
    public ExtraUserServiceImpl(ExtraUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationDto) throws MismatchingPasswordsException {
        ExtraUser newUser = ExtraUser.from(userRegistrationDto);
        userRepository.save(newUser);
        // Do something with registered user
        return new UserRegistrationResponse("User created successfully");
    }
}
