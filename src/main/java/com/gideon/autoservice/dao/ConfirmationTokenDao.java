package com.gideon.autoservice.dao;

import com.gideon.autoservice.entities.UserConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenDao extends JpaRepository<UserConfirmationToken, String> {

    Optional<UserConfirmationToken> findByConfirmationToken(String token);
}
