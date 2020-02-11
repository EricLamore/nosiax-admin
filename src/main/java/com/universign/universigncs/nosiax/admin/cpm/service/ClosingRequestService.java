package com.universign.universigncs.nosiax.admin.cpm.service;

import com.universign.universigncs.nosiax.admin.cpm.domain.ClosingRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ClosingRequest}.
 */
public interface ClosingRequestService {

    /**
     * Save a closingRequest.
     *
     * @param closingRequest the entity to save.
     * @return the persisted entity.
     */
    ClosingRequest save(ClosingRequest closingRequest);

    /**
     * Get all the closingRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClosingRequest> findAll(Pageable pageable);

    /**
     * Get the "id" closingRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClosingRequest> findOne(Long id);

    /**
     * Delete the "id" closingRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
