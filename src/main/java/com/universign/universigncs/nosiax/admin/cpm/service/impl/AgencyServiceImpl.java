package com.universign.universigncs.nosiax.admin.cpm.service.impl;

import com.universign.universigncs.nosiax.admin.cpm.service.AgencyService;
import com.universign.universigncs.nosiax.admin.cpm.domain.Agency;
import com.universign.universigncs.nosiax.admin.cpm.repository.AgencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Agency}.
 */
@Service
@Transactional
public class AgencyServiceImpl implements AgencyService {

    private final Logger log = LoggerFactory.getLogger(AgencyServiceImpl.class);

    private final AgencyRepository agencyRepository;

    public AgencyServiceImpl(AgencyRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }

    /**
     * Save a agency.
     *
     * @param agency the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Agency save(Agency agency) {
        log.debug("Request to save Agency : {}", agency);
        return agencyRepository.save(agency);
    }

    /**
     * Get all the agencies.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Agency> findAll() {
        log.debug("Request to get all Agencies");
        return agencyRepository.findAll();
    }

    /**
     * Get one agency by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Agency> findOne(Long id) {
        log.debug("Request to get Agency : {}", id);
        return agencyRepository.findById(id);
    }

    /**
     * Delete the agency by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Agency : {}", id);
        agencyRepository.deleteById(id);
    }
}
