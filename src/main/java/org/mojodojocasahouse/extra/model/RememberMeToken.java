package org.mojodojocasahouse.extra.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.apache.commons.codec.digest.DigestUtils;
import org.mojodojocasahouse.extra.exception.InvalidCredentialsException;
import org.mojodojocasahouse.extra.exception.SessionAlreadyRevokedException;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Entity
@Table(name = "REMEMBERME_TOKENS")
public class RememberMeToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "REVOKED", nullable = false)
    protected Boolean revoked;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    @Getter
    protected ExtraUser user;

    @Column(name = "EXP_DATE_MILL", nullable = false)
    protected Timestamp expirationDate;

    @Column(name = "SELECTOR", nullable = false, unique = true)
    @Getter
    protected String selector;

    @Column(name = "VALIDATOR", nullable = false)
    @Getter
    protected String validator;

    public RememberMeToken(){}

    public RememberMeToken(ExtraUser user){
        this.revoked = false;
        this.user = user;
        this.expirationDate = Timestamp.from(
                new Timestamp(System.currentTimeMillis())
                        .toInstant()
                        .plus(2, ChronoUnit.WEEKS)
        );
        this.selector = DigestUtils.sha256Hex(this.user.getEmail() + ':' + this.expirationDate);
        this.validator = DigestUtils.sha256Hex(this.selector + ':' + user.getPassword());
    }

    public void validate(String selector, String passwordHashHex) throws InvalidCredentialsException{
        if(
                revoked ||
                !this.matchesValidator(selector, passwordHashHex) ||
                expirationDate.before(new Timestamp(System.currentTimeMillis()))
        ){
            throw new InvalidCredentialsException();
        }
    }

    private Boolean matchesValidator(String selector, String passwordHashHex){
        return Objects.equals(this.validator, DigestUtils.sha256Hex(selector + ':' + passwordHashHex));
    }

    public void revoke() {
        if(this.revoked){
            throw new SessionAlreadyRevokedException();
        }
        this.revoked = true;
    }
}
