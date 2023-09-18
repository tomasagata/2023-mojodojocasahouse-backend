package org.mojodojocasahouse.extra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

import org.mojodojocasahouse.extra.model.impl.ExtraUser;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface ExtraUserRepository extends JpaRepository<ExtraUser,Integer>
{
    Optional<ExtraUser> findOneByEmailAndPassword(String email, String password);
    ExtraUser findByEmail(String email);
}
