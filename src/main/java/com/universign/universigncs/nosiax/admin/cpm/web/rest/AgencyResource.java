package com.universign.universigncs.nosiax.admin.cpm.web.rest;

import com.universign.universigncs.nosiax.admin.cpm.domain.Agency;
import com.universign.universigncs.nosiax.admin.cpm.service.AgencyService;
import com.universign.universigncs.nosiax.admin.cpm.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.universign.universigncs.nosiax.admin.cpm.domain.Agency}.
 */
@RestController
@RequestMapping("/api")
public class AgencyResource {

    private final Logger log = LoggerFactory.getLogger(AgencyResource.class);

    private static final String ENTITY_NAME = "agency";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgencyService agencyService;

    public AgencyResource(AgencyService agencyService) {
        this.agencyService = agencyService;
    }

    /**
     * {@code POST  /agencies} : Create a new agency.
     *
     * @param agency the agency to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agency, or with status {@code 400 (Bad Request)} if the agency has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/agencies")
    public ResponseEntity<Agency> createAgency(@RequestBody Agency agency) throws URISyntaxException {
        log.debug("REST request to save Agency : {}", agency);
        if (agency.getId() != null) {
            throw new BadRequestAlertException("A new agency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Agency result = agencyService.save(agency);
        return ResponseEntity.created(new URI("/api/agencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /agencies} : Updates an existing agency.
     *
     * @param agency the agency to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agency,
     * or with status {@code 400 (Bad Request)} if the agency is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agency couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/agencies")
    public ResponseEntity<Agency> updateAgency(@RequestBody Agency agency) throws URISyntaxException {
        log.debug("REST request to update Agency : {}", agency);
        if (agency.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Agency result = agencyService.save(agency);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, agency.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /agencies} : get all the agencies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agencies in body.
     */
    @GetMapping("/agencies")
    public List<Agency> getAllAgencies() {
        log.debug("REST request to get all Agencies");
        return agencyService.findAll();
    }

    /**
     * {@code GET  /agencies/:id} : get the "id" agency.
     *
     * @param id the id of the agency to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agency, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/agencies/{id}")
    public ResponseEntity<Agency> getAgency(@PathVariable Long id) {
        log.debug("REST request to get Agency : {}", id);
        Optional<Agency> agency = agencyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agency);
    }

    /**
     * {@code DELETE  /agencies/:id} : delete the "id" agency.
     *
     * @param id the id of the agency to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/agencies/{id}")
    public ResponseEntity<Void> deleteAgency(@PathVariable Long id) {
        log.debug("REST request to delete Agency : {}", id);
        agencyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
