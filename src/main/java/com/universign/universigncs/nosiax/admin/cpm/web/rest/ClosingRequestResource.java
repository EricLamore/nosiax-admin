package com.universign.universigncs.nosiax.admin.cpm.web.rest;

import com.universign.universigncs.nosiax.admin.cpm.domain.ClosingRequest;
import com.universign.universigncs.nosiax.admin.cpm.repository.ClosingRequestRepository;
import com.universign.universigncs.nosiax.admin.cpm.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.universign.universigncs.nosiax.admin.cpm.domain.ClosingRequest}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ClosingRequestResource {

    private final Logger log = LoggerFactory.getLogger(ClosingRequestResource.class);

    private static final String ENTITY_NAME = "closingRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClosingRequestRepository closingRequestRepository;

    public ClosingRequestResource(ClosingRequestRepository closingRequestRepository) {
        this.closingRequestRepository = closingRequestRepository;
    }

    /**
     * {@code POST  /closing-requests} : Create a new closingRequest.
     *
     * @param closingRequest the closingRequest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new closingRequest, or with status {@code 400 (Bad Request)} if the closingRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/closing-requests")
    public ResponseEntity<ClosingRequest> createClosingRequest(@RequestBody ClosingRequest closingRequest) throws URISyntaxException {
        log.debug("REST request to save ClosingRequest : {}", closingRequest);
        if (closingRequest.getId() != null) {
            throw new BadRequestAlertException("A new closingRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClosingRequest result = closingRequestRepository.save(closingRequest);
        return ResponseEntity.created(new URI("/api/closing-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /closing-requests} : Updates an existing closingRequest.
     *
     * @param closingRequest the closingRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated closingRequest,
     * or with status {@code 400 (Bad Request)} if the closingRequest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the closingRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/closing-requests")
    public ResponseEntity<ClosingRequest> updateClosingRequest(@RequestBody ClosingRequest closingRequest) throws URISyntaxException {
        log.debug("REST request to update ClosingRequest : {}", closingRequest);
        if (closingRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClosingRequest result = closingRequestRepository.save(closingRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, closingRequest.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /closing-requests} : get all the closingRequests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of closingRequests in body.
     */
    @GetMapping("/closing-requests")
    public ResponseEntity<List<ClosingRequest>> getAllClosingRequests(Pageable pageable) {
        log.debug("REST request to get a page of ClosingRequests");
        Page<ClosingRequest> page = closingRequestRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /closing-requests/:id} : get the "id" closingRequest.
     *
     * @param id the id of the closingRequest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the closingRequest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/closing-requests/{id}")
    public ResponseEntity<ClosingRequest> getClosingRequest(@PathVariable Long id) {
        log.debug("REST request to get ClosingRequest : {}", id);
        Optional<ClosingRequest> closingRequest = closingRequestRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(closingRequest);
    }

    /**
     * {@code DELETE  /closing-requests/:id} : delete the "id" closingRequest.
     *
     * @param id the id of the closingRequest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/closing-requests/{id}")
    public ResponseEntity<Void> deleteClosingRequest(@PathVariable Long id) {
        log.debug("REST request to delete ClosingRequest : {}", id);
        closingRequestRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
