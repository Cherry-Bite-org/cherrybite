package com.cherrybite.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cherrybite.entity.OtpVerification;

public interface OtpVerificationRepository extends JpaRepository<OtpVerification, UUID> {

  Optional<OtpVerification> findTopByIdentifierOrderByCreatedAtDesc(String identifier);

  Optional<OtpVerification> findTopByTemporaryTokenOrderByCreatedAtDesc(String temporaryToken);
}
