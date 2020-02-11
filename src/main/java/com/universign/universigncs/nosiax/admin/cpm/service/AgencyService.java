package com.universign.universigncs.nosiax.admin.cpm.service;

import com.universign.universigncs.nosiax.admin.cpm.domain.Agency;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Agency}.
 */
public interface AgencyService {

    /**
     * Save a agency.
     *
     * @param agency the entity to save.
     * @return the persisted entity.
     */
    Agency save(Agency agency);

    /**
     * Get all the agencies.
     *
     * @return the list of entities.
     */
    List<Agency> findAll();

    /**
     * Get the "id" agency.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Agency> findOne(Long id);

    /**
     * Delete the "id" agency.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
