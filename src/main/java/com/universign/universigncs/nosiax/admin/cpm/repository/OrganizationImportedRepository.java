package com.universign.universigncs.nosiax.admin.cpm.repository;

import com.universign.universigncs.nosiax.admin.cpm.domain.OrganizationImported;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrganizationImported entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationImportedRepository extends JpaRepository<OrganizationImported, Long> {}
