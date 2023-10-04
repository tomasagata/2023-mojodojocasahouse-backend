package org.mojodojocasahouse.extra.testmodels;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.mojodojocasahouse.extra.model.ExtraUser;
import org.mojodojocasahouse.extra.model.RememberMeToken;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "REMEMBERME_TOKENS")
public class TestRememberMeToken extends RememberMeToken {

    public TestRememberMeToken(Long id, Boolean revoked, ExtraUser user,
                               Integer secondsToExpire, String selector, String validator){
        this.id = id;
        this.revoked = revoked;
        this.user = user;
        this.expirationDate = Timestamp.from(
                new Timestamp(System.currentTimeMillis())
                        .toInstant()
                        .plus(secondsToExpire, ChronoUnit.SECONDS)
        );
        this.selector = selector;
        this.validator = validator;
    }

    public TestRememberMeToken(){}

}
