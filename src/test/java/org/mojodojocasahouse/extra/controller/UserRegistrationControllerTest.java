package org.mojodojocasahouse.extra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotBlank;
import org.mojodojocasahouse.extra.dto.UserRegistrationRequest;
import org.mojodojocasahouse.extra.dto.UserRegistrationResponse;
import org.mojodojocasahouse.extra.exception.MismatchingPasswordsException;
import org.mojodojocasahouse.extra.exception.handler.UserRegistrationExceptionHandler;
import org.mojodojocasahouse.extra.exception.handler.helper.ApiError;
import org.mojodojocasahouse.extra.service.ExtraUserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mojodojocasahouse.extra.service.impl.ExtraUserServiceImpl;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserRegistrationControllerTest {

    private MockMvc mvc;

    private JacksonTester<ApiError> jsonApiError;

    @Mock
    public ExtraUserServiceImpl service;

    @InjectMocks
    public UserRegistrationController controller;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());

        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new UserRegistrationExceptionHandler())
                .build();
    }

    @Test
    public void testPostingUnregisteredUserShouldReturnSuccessResponse() throws Exception {
        // Setup - data
        UserRegistrationRequest unregisteredUserDto = new UserRegistrationRequest(
                "Michael",
                "Jordan",
                "mj@me.com",
                "Somepassword1!",
                "Somepassword1!"
        );
        UserRegistrationResponse responseDto = new UserRegistrationResponse(
                "User created successfully"
        );

        // Setup - expectations
        given(service.registerUser(unregisteredUserDto)).willReturn(responseDto);

        // exercise
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.
                    post("/register")
                    .content(asJsonString(unregisteredUserDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.ALL))
                .andReturn().getResponse();

        // verify
//        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
//        Assertions.assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        Assertions.assertThat(response.getContentAsString()).isEqualTo(asJsonString(responseDto));
    }

    @Test
    public void testRegisteringANewUserWithSpecialCharacteredFirstNameReturnsBadRequest() throws Exception {
        // Setup - data
        UserRegistrationRequest unregisteredUserDto = new UserRegistrationRequest(
                "@llein",
                "Jordan",
                "mj@me.com",
                "Somepassword1!",
                "Somepassword1!"
        );
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Data validation error",
                "firstName: First name must not be left blank or contain special characters or numbers"
        );

        // exercise
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.
                        post("/register")
                        .content(asJsonString(unregisteredUserDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andReturn().getResponse();

        // verify
        assertThatResponseReturnsError(response, apiError);
    }

    @Test
    public void testRegisteringANewUserWithEmptyFirstNameReturnsBadRequest() throws Exception {
        // Setup - data
        UserRegistrationRequest unregisteredUserDto = new UserRegistrationRequest(
                "",
                "Jordan",
                "mj@me.com",
                "Somepassword1!",
                "Somepassword1!"
        );
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Data validation error",
                "firstName: First name must not be left blank or contain special characters or numbers"
        );

        // exercise
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.
                        post("/register")
                        .content(asJsonString(unregisteredUserDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andReturn().getResponse();

        // verify
        assertThatResponseReturnsError(response, apiError);
    }

    @Test
    public void testRegisteringANewUserWithNullFirstNameReturnsBadRequest() throws Exception {
        // Setup - data
        UserRegistrationRequest unregisteredUserDto = new UserRegistrationRequest(
                null,
                "Jordan",
                "mj@me.com",
                "Somepassword1!",
                "Somepassword1!"
        );
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Data validation error",
                "firstName: First name is mandatory"
        );

        // exercise
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.
                        post("/register")
                        .content(asJsonString(unregisteredUserDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andReturn().getResponse();

        // verify
        assertThatResponseReturnsError(response, apiError);
    }

    @Test
    public void testRegisteringANewUserWithSpecialCharacteredLastNameReturnsBadRequest() throws Exception {
        // Setup - data
        UserRegistrationRequest unregisteredUserDto = new UserRegistrationRequest(
                "Klein",
                "J0rdan",
                "mj@me.com",
                "Somepassword1!",
                "Somepassword1!"
        );
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Data validation error",
                "lastName: Last name must not be left blank or contain special characters or numbers"
        );

        // exercise
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.
                        post("/register")
                        .content(asJsonString(unregisteredUserDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andReturn().getResponse();

        // verify
        assertThatResponseReturnsError(response, apiError);
    }

    @Test
    public void testRegisteringANewUserWithEmptyLastNameReturnsBadRequest() throws Exception {
        // Setup - data
        UserRegistrationRequest unregisteredUserDto = new UserRegistrationRequest(
                "Michael",
                "",
                "mj@me.com",
                "Somepassword1!",
                "Somepassword1!"
        );
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Data validation error",
                "lastName: Last name must not be left blank or contain special characters or numbers"
        );

        // exercise
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.
                        post("/register")
                        .content(asJsonString(unregisteredUserDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andReturn().getResponse();

        // verify
        assertThatResponseReturnsError(response, apiError);
    }

    @Test
    public void testRegisteringANewUserWithNullLastNameReturnsBadRequest() throws Exception {
        // Setup - data
        UserRegistrationRequest unregisteredUserDto = new UserRegistrationRequest(
                "Michael",
                null,
                "mj@me.com",
                "Somepassword1!",
                "Somepassword1!"
        );
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Data validation error",
                "lastName: Last name is mandatory"
        );

        // exercise
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.
                        post("/register")
                        .content(asJsonString(unregisteredUserDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andReturn().getResponse();

        // verify
        assertThatResponseReturnsError(response, apiError);
    }

    @Test
    public void testRegisteringANewUserWithAWrongEmailReturnsBadRequest() throws Exception {
        // Setup - data
        UserRegistrationRequest unregisteredUserDto = new UserRegistrationRequest(
                "Michael",
                "Jordan",
                "@.",
                "Somepassword1!",
                "Somepassword1!"
        );
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Data validation error",
                "email: Email must be valid"
        );

        // exercise
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.
                        post("/register")
                        .content(asJsonString(unregisteredUserDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andReturn().getResponse();

        // verify
        assertThatResponseReturnsError(response, apiError);
    }

    @Test
    public void testRegisteringANewUserWithABlankEmailReturnsBadRequest() throws Exception {
        // Setup - data
        UserRegistrationRequest unregisteredUserDto = new UserRegistrationRequest(
                "Michael",
                "Jordan",
                "",
                "Somepassword1!",
                "Somepassword1!"
        );
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Data validation error",
                "email: Email must not be left blank"
        );

        // exercise
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.
                        post("/register")
                        .content(asJsonString(unregisteredUserDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andReturn().getResponse();

        // verify
        assertThatResponseReturnsError(response, apiError);
    }

    @Test
    public void testRegisteringANewUserWithNullEmailReturnsBadRequest() throws Exception {
        // Setup - data
        UserRegistrationRequest unregisteredUserDto = new UserRegistrationRequest(
                "Michael",
                "Jordan",
                null,
                "Somepassword1!",
                "Somepassword1!"
        );
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Data validation error",
                Arrays.asList(
                    "email: Email is mandatory",
                    "email: Email must not be left blank"
                )
        );

        // exercise
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.
                        post("/register")
                        .content(asJsonString(unregisteredUserDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andReturn().getResponse();

        // verify
        assertThatResponseReturnsError(response, apiError);
    }


    @Test
    public void testRegisteringANewUserWithWeakPasswordReturnsBadRequest() throws Exception {
        // Setup - data
        UserRegistrationRequest unregisteredUserDto = new UserRegistrationRequest(
                "Michael",
                "Jordan",
                "mj@me.com",
                "password",
                "password"
        );
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Data validation error",
                Arrays.asList(
                        "passwordRepeat: Password must contain eight characters, one letter, one number and one of the following: @$!%*#?",
                        "password: Password must contain eight characters, one letter, one number and one of the following: @$!%*#?"
                )
        );

        // exercise
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.
                        post("/register")
                        .content(asJsonString(unregisteredUserDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andReturn().getResponse();

        // verify
        assertThatResponseReturnsError(response, apiError);
    }

    @Test
    public void testRegisteringANewUserWithEmptyPasswordReturnsBadRequest() throws Exception {
        // Setup - data
        UserRegistrationRequest unregisteredUserDto = new UserRegistrationRequest(
                "Michael",
                "Jordan",
                "mj@me.com",
                "",
                ""
        );
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Data validation error",
                Arrays.asList(
                        "passwordRepeat: Password must contain eight characters, one letter, one number and one of the following: @$!%*#?",
                        "password: Password must contain eight characters, one letter, one number and one of the following: @$!%*#?"
                )
        );

        // exercise
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.
                        post("/register")
                        .content(asJsonString(unregisteredUserDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andReturn().getResponse();

        // verify
        assertThatResponseReturnsError(response, apiError);
    }

    @Test
    public void testRegisteringANewUserWithNullPasswordReturnsBadRequest() throws Exception {
        // Setup - data
        UserRegistrationRequest unregisteredUserDto = new UserRegistrationRequest(
                "Michael",
                "Jordan",
                "mj@me.com",
                null,
                null
        );
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Data validation error",
                Arrays.asList(
                        "passwordRepeat: Repeating password is mandatory",
                        "password: Password is mandatory"
                )
        );

        // exercise
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.
                        post("/register")
                        .content(asJsonString(unregisteredUserDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andReturn().getResponse();

        // verify
        assertThatResponseReturnsError(response, apiError);
    }

    @Test
    public void testRegisteringANewUserWithIncorrectlyRepeatedPasswordReturnsBadRequest() throws Exception {
        // Setup - data
        UserRegistrationRequest unregisteredUserDto = new UserRegistrationRequest(
                "Michael",
                "Jordan",
                "mj@me.com",
                "Somepassword1!",
                "Anotherpassword2!"
        );
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Data validation error",
                "passwordRepeat: Passwords must match"
        );

        // Setup - expectations
        given(service.registerUser(any(UserRegistrationRequest.class))).willThrow(MismatchingPasswordsException.class);

        // exercise
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.
                        post("/register")
                        .content(asJsonString(unregisteredUserDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andReturn().getResponse();

        // verify
        assertThatResponseReturnsError(response, apiError);
    }

    private void assertThatResponseReturnsError(MockHttpServletResponse response, ApiError expectedApiError) throws Exception {
        ApiError actualApiError = jsonApiError.parse(response.getContentAsString()).getObject();

        Assertions.assertThat(actualApiError.getMessage()).isEqualTo(expectedApiError.getMessage());
        Assertions.assertThat(actualApiError.getStatus()).isEqualTo(expectedApiError.getStatus());
        Assertions.assertThat(actualApiError.getErrors().toArray()).containsExactlyInAnyOrder(expectedApiError.getErrors().toArray());
    }

}