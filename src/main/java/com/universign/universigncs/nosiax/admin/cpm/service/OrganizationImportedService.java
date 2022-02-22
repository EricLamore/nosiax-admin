package com.universign.universigncs.nosiax.admin.cpm.service;

import com.universign.universigncs.nosiax.admin.cpm.domain.OrganizationImported;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link OrganizationImported}.
 */
public interface OrganizationImportedService {
  /**
   * Save a organizationImported.
   *
   * @param organizationImported the entity to save.
   * @return the persisted entity.
   */
  OrganizationImported save(OrganizationImported organizationImported);

  /**
   * Partially updates a organizationImported.
   *
   * @param organizationImported the entity to update partially.
   * @return the persisted entity.
   */
  Optional<OrganizationImported> partialUpdate(OrganizationImported organizationImported);

  /**
   * Get all the organizationImporteds.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<OrganizationImported> findAll(Pageable pageable);

  /**
   * Get the "id" organizationImported.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<OrganizationImported> findOne(Long id);

  /**
   * Delete the "id" organizationImported.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
