package com.universign.universigncs.nosiax.admin.cpm.service.impl;

import com.universign.universigncs.nosiax.admin.cpm.service.RaRecordService;
import com.universign.universigncs.nosiax.admin.cpm.domain.RaRecord;
import com.universign.universigncs.nosiax.admin.cpm.repository.RaRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RaRecord}.
 */
@Service
@Transactional
public class RaRecordServiceImpl implements RaRecordService {

    private final Logger log = LoggerFactory.getLogger(RaRecordServiceImpl.class);

    private final RaRecordRepository raRecordRepository;

    public RaRecordServiceImpl(RaRecordRepository raRecordRepository) {
        this.raRecordRepository = raRecordRepository;
    }

    /**
     * Save a raRecord.
     *
     * @param raRecord the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RaRecord save(RaRecord raRecord) {
        log.debug("Request to save RaRecord : {}", raRecord);
        return raRecordRepository.save(raRecord);
    }

    /**
     * Get all the raRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RaRecord> findAll(Pageable pageable) {
        log.debug("Request to get all RaRecords");
        return raRecordRepository.findAll(pageable);
    }

    /**
     * Get one raRecord by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RaRecord> findOne(Long id) {
        log.debug("Request to get RaRecord : {}", id);
        return raRecordRepository.findById(id);
    }

    /**
     * Delete the raRecord by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RaRecord : {}", id);
        raRecordRepository.deleteById(id);
    }
}
