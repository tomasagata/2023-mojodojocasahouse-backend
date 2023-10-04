package org.mojodojocasahouse.extra.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.mojodojocasahouse.extra.dto.*;
import org.mojodojocasahouse.extra.exception.MissingRequestParameterException;
import org.mojodojocasahouse.extra.model.CookieCollection;
import org.mojodojocasahouse.extra.model.ExtraUser;
import org.mojodojocasahouse.extra.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class AuthenticationController {

    private final AuthenticationService userService;

    @Autowired
    public AuthenticationController(AuthenticationService userService) {
        this.userService = userService;
    }


    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ApiResponse> registerUserAccount(
            @Valid @RequestBody UserRegistrationRequest userRegistrationRequest)
    {
            ApiResponse response = userService.registerUser(userRegistrationRequest);
            return new ResponseEntity<>(
                    response,
                    HttpStatus.CREATED
            );
    }

    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ApiResponse> loginUser(
            @Valid @RequestBody(required = false) UserAuthenticationRequest userAuthenticationRequest,
            @CookieValue(value = "remember-me", required = false) String rememberMeCookie,
            HttpServletResponse servletResponse) {

        Pair<ApiResponse, CookieCollection> responseCookiePair;

        if (rememberMeCookie != null && !rememberMeCookie.isBlank()){
            responseCookiePair = userService.authenticateUser(rememberMeCookie);
        } else if (userAuthenticationRequest != null) {
            responseCookiePair = userService.authenticateUser(userAuthenticationRequest);
        } else {
            throw new MissingRequestParameterException();
        }

        for ( Cookie c: responseCookiePair.getSecond().getCookies()){
            servletResponse.addCookie(
                    c
            );
        }

        // Return response
        return new ResponseEntity<>(
                responseCookiePair.getFirst(),
                HttpStatus.OK
        );
    }


    @GetMapping(path = "/protected", produces = "application/json")
    public ResponseEntity<ApiResponse> protectedResource(@CookieValue("JSESSIONID") UUID sessionId)
    {
        userService.validateSession(sessionId);
        return new ResponseEntity<>(
                new ApiResponse("Authenticated and authorized!"),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "/logout", produces = "application/json")
    public ResponseEntity<ApiResponse> logoutUser(
            @CookieValue("JSESSIONID") UUID sessionId,
            HttpServletResponse servletResponse,
            @CookieValue(value = "remember-me", required = false) String rememberMeCookie
    )
    {
        userService.validateSession(sessionId);
        userService.revokeCredentials(sessionId, rememberMeCookie);

        // Create a new session cookie with zero life to delete the existing session cookie in the client.
        Cookie zeroTtlSessionCookie = new Cookie("JSESSIONID", null);
        zeroTtlSessionCookie.setMaxAge(0);

        // Create a new session cookie with zero life to delete the existing session cookie in the client.
        Cookie zeroTtlRememberMeCookie = new Cookie("remember-me", null);
        zeroTtlSessionCookie.setMaxAge(0);

        // Append Set-Cookie header to response
        servletResponse.addCookie(
                zeroTtlSessionCookie
        );
        servletResponse.addCookie(
                zeroTtlRememberMeCookie
        );

        return new ResponseEntity<>(
                new ApiResponse("User logout successful"),
                HttpStatus.OK
        );
    }

    @PostMapping(path = "/auth/password/change", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ApiResponse> changePassword(@Valid @RequestBody UserChangePasswordRequest userChangePasswordRequest,@CookieValue("JSESSIONID") UUID cookie){
        userService.validateSession(cookie);
        ExtraUser user = userService.getUserBySessionToken(cookie);
        ApiResponse response = userService.changePassword(user,userChangePasswordRequest);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

}
