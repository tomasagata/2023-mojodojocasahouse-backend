package org.mojodojocasahouse.extra.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import org.mojodojocasahouse.extra.dto.UserRegistrationRequest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "USERS")
@Getter
public class ExtraUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Setter
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Setter
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Setter
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @Setter
    @Column(name = "idExpense", nullable = false)
    private Set <ExtraExpense> idExpense;

    public ExtraUser() {}

    public ExtraUser(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public static ExtraUser from(UserRegistrationRequest userRegistrationDto, String encodedPassword){
        return new ExtraUser(
                userRegistrationDto.getFirstName(),
                userRegistrationDto.getLastName(),
                userRegistrationDto.getEmail(),
                encodedPassword
                );
    }
}