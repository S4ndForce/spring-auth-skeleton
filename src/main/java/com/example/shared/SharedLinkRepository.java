package com.example.shared;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.Instant;
import java.util.Optional;

public interface SharedLinkRepository extends JpaRepository<SharedLink, Long>, JpaSpecificationExecutor<SharedLink> {
    Optional<SharedLink> findByToken(String token);
    int deleteByExpiresAtBefore(Instant now);
}
