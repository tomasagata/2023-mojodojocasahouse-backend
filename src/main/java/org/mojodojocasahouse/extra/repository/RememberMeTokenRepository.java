package org.mojodojocasahouse.extra.repository;

import org.mojodojocasahouse.extra.model.RememberMeToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RememberMeTokenRepository extends JpaRepository<RememberMeToken, Long> {

    Optional<RememberMeToken> findBySelector(String selector);

}
