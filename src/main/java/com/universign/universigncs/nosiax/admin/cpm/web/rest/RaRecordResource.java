package com.universign.universigncs.nosiax.admin.cpm.web.rest;

import com.universign.universigncs.nosiax.admin.cpm.domain.RaRecord;
import com.universign.universigncs.nosiax.admin.cpm.service.RaRecordService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.universign.universigncs.nosiax.admin.cpm.domain.RaRecord}.
 */
@RestController
@RequestMapping("/api")
public class RaRecordResource {

    private final Logger log = LoggerFactory.getLogger(RaRecordResource.class);

    private static final String ENTITY_NAME = "raRecord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RaRecordService raRecordService;

    public RaRecordResource(RaRecordService raRecordService) {
        this.raRecordService = raRecordService;
    }

    /**
     * {@code POST  /ra-records} : Create a new raRecord.
     *
     * @param raRecord the raRecord to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new raRecord, or with status {@code 400 (Bad Request)} if the raRecord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ra-records")
    public ResponseEntity<RaRecord> createRaRecord(@Valid @RequestBody RaRecord raRecord) throws URISyntaxException {
        log.debug("REST request to save RaRecord : {}", raRecord);
        if (raRecord.getId() != null) {
            throw new BadRequestAlertException("A new raRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RaRecord result = raRecordService.save(raRecord);
        return ResponseEntity.created(new URI("/api/ra-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ra-records} : Updates an existing raRecord.
     *
     * @param raRecord the raRecord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated raRecord,
     * or with status {@code 400 (Bad Request)} if the raRecord is not valid,
     * or with status {@code 500 (Internal Server Error)} if the raRecord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ra-records")
    public ResponseEntity<RaRecord> updateRaRecord(@Valid @RequestBody RaRecord raRecord) throws URISyntaxException {
        log.debug("REST request to update RaRecord : {}", raRecord);
        if (raRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RaRecord result = raRecordService.save(raRecord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, raRecord.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ra-records} : get all the raRecords.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of raRecords in body.
     */
    @GetMapping("/ra-records")
    public ResponseEntity<List<RaRecord>> getAllRaRecords(Pageable pageable) {
        log.debug("REST request to get a page of RaRecords");
        Page<RaRecord> page = raRecordService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ra-records/:id} : get the "id" raRecord.
     *
     * @param id the id of the raRecord to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the raRecord, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ra-records/{id}")
    public ResponseEntity<RaRecord> getRaRecord(@PathVariable Long id) {
        log.debug("REST request to get RaRecord : {}", id);
        Optional<RaRecord> raRecord = raRecordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(raRecord);
    }

    /**
     * {@code DELETE  /ra-records/:id} : delete the "id" raRecord.
     *
     * @param id the id of the raRecord to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ra-records/{id}")
    public ResponseEntity<Void> deleteRaRecord(@PathVariable Long id) {
        log.debug("REST request to delete RaRecord : {}", id);
        raRecordService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
