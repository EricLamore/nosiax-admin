package com.universign.universigncs.nosiax.admin.cpm.service;

import com.universign.universigncs.nosiax.admin.cpm.domain.RaRecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link RaRecord}.
 */
public interface RaRecordService {

    /**
     * Save a raRecord.
     *
     * @param raRecord the entity to save.
     * @return the persisted entity.
     */
    RaRecord save(RaRecord raRecord);

    /**
     * Get all the raRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RaRecord> findAll(Pageable pageable);


    /**
     * Get the "id" raRecord.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RaRecord> findOne(Long id);

    /**
     * Delete the "id" raRecord.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
