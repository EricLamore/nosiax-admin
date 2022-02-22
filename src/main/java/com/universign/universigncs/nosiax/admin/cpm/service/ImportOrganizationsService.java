package com.universign.universigncs.nosiax.admin.cpm.service;

import com.universign.universigncs.nosiax.admin.cpm.domain.ImportOrganizations;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ImportOrganizations}.
 */
public interface ImportOrganizationsService {
  /**
   * Save a importOrganizations.
   *
   * @param importOrganizations the entity to save.
   * @return the persisted entity.
   */
  ImportOrganizations save(ImportOrganizations importOrganizations);

  /**
   * Partially updates a importOrganizations.
   *
   * @param importOrganizations the entity to update partially.
   * @return the persisted entity.
   */
  Optional<ImportOrganizations> partialUpdate(ImportOrganizations importOrganizations);

  /**
   * Get all the importOrganizations.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<ImportOrganizations> findAll(Pageable pageable);

  /**
   * Get the "id" importOrganizations.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<ImportOrganizations> findOne(Long id);

  /**
   * Delete the "id" importOrganizations.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
