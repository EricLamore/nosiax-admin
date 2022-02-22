package com.universign.universigncs.nosiax.admin.cpm.repository;

import com.universign.universigncs.nosiax.admin.cpm.domain.ImportOrganizations;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ImportOrganizations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImportOrganizationsRepository extends JpaRepository<ImportOrganizations, Long> {}
