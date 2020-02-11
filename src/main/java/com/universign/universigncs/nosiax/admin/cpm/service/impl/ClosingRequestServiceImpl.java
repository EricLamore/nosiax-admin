package com.universign.universigncs.nosiax.admin.cpm.service.impl;

import com.universign.universigncs.nosiax.admin.cpm.service.ClosingRequestService;
import com.universign.universigncs.nosiax.admin.cpm.domain.ClosingRequest;
import com.universign.universigncs.nosiax.admin.cpm.repository.ClosingRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ClosingRequest}.
 */
@Service
@Transactional
public class ClosingRequestServiceImpl implements ClosingRequestService {

    private final Logger log = LoggerFactory.getLogger(ClosingRequestServiceImpl.class);

    private final ClosingRequestRepository closingRequestRepository;

    public ClosingRequestServiceImpl(ClosingRequestRepository closingRequestRepository) {
        this.closingRequestRepository = closingRequestRepository;
    }

    /**
     * Save a closingRequest.
     *
     * @param closingRequest the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ClosingRequest save(ClosingRequest closingRequest) {
        log.debug("Request to save ClosingRequest : {}", closingRequest);
        return closingRequestRepository.save(closingRequest);
    }

    /**
     * Get all the closingRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClosingRequest> findAll(Pageable pageable) {
        log.debug("Request to get all ClosingRequests");
        return closingRequestRepository.findAll(pageable);
    }

    /**
     * Get one closingRequest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClosingRequest> findOne(Long id) {
        log.debug("Request to get ClosingRequest : {}", id);
        return closingRequestRepository.findById(id);
    }

    /**
     * Delete the closingRequest by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClosingRequest : {}", id);
        closingRequestRepository.deleteById(id);
    }
}
