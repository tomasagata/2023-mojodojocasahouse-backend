package org.mojodojocasahouse.extra.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mojodojocasahouse.extra.exception.ExistingUserEmailException;
import org.mojodojocasahouse.extra.model.impl.ExtraUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class ExtraUserRepositoryTest {

    @Autowired
    private ExtraUserRepository repo;

    @Test
    void testFindingAUserByEmailReturnsOne() {
        // Setup - data
        ExtraUser savedUser = repo.save(
                new ExtraUser(
                    "Some",
                    "User",
                    "mj@me.com",
                    "a_password"
                )
        );

        // execute
        Optional<ExtraUser> foundUser = repo.findByEmail("mj@me.com");

        // verify
        Assertions.assertThat(foundUser).isEqualTo(Optional.of(savedUser));
    }

    @Test
    void testSavingASuerWithANonUniqueEmailAddressThrowsDataIntegrityViolationException() {
        // Setup - data
        ExtraUser firstSavedUser = repo.save(
                new ExtraUser(
                        "Some",
                        "User",
                        "mj@me.com",
                        "a_password"
                )
        );
        ExtraUser userWithExistingEmail = new ExtraUser(
                    "Michael",
                    "Jackson",
                    "mj@me.com",
                    "another_password"
        );


        // execute and verify
        Assertions
                .assertThatThrownBy(() -> {repo.save(userWithExistingEmail);})
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}