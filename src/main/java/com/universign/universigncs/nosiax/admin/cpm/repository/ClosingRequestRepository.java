package com.universign.universigncs.nosiax.admin.cpm.repository;

import com.universign.universigncs.nosiax.admin.cpm.domain.ClosingRequest;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ClosingRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClosingRequestRepository extends JpaRepository<ClosingRequest, Long> {

}
