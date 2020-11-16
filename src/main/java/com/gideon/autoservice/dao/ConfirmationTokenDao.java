package com.gideon.autoservice.dao;

import com.gideon.autoservice.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenDao extends JpaRepository<ConfirmationToken, String> {

    Optional<ConfirmationToken> findByConfirmationToken(String token);
}
