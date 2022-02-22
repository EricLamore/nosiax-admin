package com.universign.universigncs.nosiax.admin.cpm.web.rest;

import com.universign.universigncs.nosiax.admin.cpm.domain.ImportOrganizations;
import com.universign.universigncs.nosiax.admin.cpm.repository.ImportOrganizationsRepository;
import com.universign.universigncs.nosiax.admin.cpm.service.ImportOrganizationsService;
import com.universign.universigncs.nosiax.admin.cpm.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.universign.universigncs.nosiax.admin.cpm.domain.ImportOrganizations}.
 */
@RestController
@RequestMapping("/api")
public class ImportOrganizationsResource {

  private final Logger log = LoggerFactory.getLogger(ImportOrganizationsResource.class);

  private static final String ENTITY_NAME = "importOrganizations";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final ImportOrganizationsService importOrganizationsService;

  private final ImportOrganizationsRepository importOrganizationsRepository;

  public ImportOrganizationsResource(
    ImportOrganizationsService importOrganizationsService,
    ImportOrganizationsRepository importOrganizationsRepository
  ) {
    this.importOrganizationsService = importOrganizationsService;
    this.importOrganizationsRepository = importOrganizationsRepository;
  }

  /**
   * {@code POST  /import-organizations} : Create a new importOrganizations.
   *
   * @param importOrganizations the importOrganizations to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new importOrganizations, or with status {@code 400 (Bad Request)} if the importOrganizations has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/import-organizations")
  public ResponseEntity<ImportOrganizations> createImportOrganizations(@RequestBody ImportOrganizations importOrganizations)
    throws URISyntaxException {
    log.debug("REST request to save ImportOrganizations : {}", importOrganizations);
    if (importOrganizations.getId() != null) {
      throw new BadRequestAlertException("A new importOrganizations cannot already have an ID", ENTITY_NAME, "idexists");
    }
    ImportOrganizations result = importOrganizationsService.save(importOrganizations);
    return ResponseEntity
      .created(new URI("/api/import-organizations/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /import-organizations/:id} : Updates an existing importOrganizations.
   *
   * @param id the id of the importOrganizations to save.
   * @param importOrganizations the importOrganizations to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated importOrganizations,
   * or with status {@code 400 (Bad Request)} if the importOrganizations is not valid,
   * or with status {@code 500 (Internal Server Error)} if the importOrganizations couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/import-organizations/{id}")
  public ResponseEntity<ImportOrganizations> updateImportOrganizations(
    @PathVariable(value = "id", required = false) final Long id,
    @RequestBody ImportOrganizations importOrganizations
  ) throws URISyntaxException {
    log.debug("REST request to update ImportOrganizations : {}, {}", id, importOrganizations);
    if (importOrganizations.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, importOrganizations.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!importOrganizationsRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    ImportOrganizations result = importOrganizationsService.save(importOrganizations);
    return ResponseEntity
      .ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, importOrganizations.getId().toString()))
      .body(result);
  }

  /**
   * {@code PATCH  /import-organizations/:id} : Partial updates given fields of an existing importOrganizations, field will ignore if it is null
   *
   * @param id the id of the importOrganizations to save.
   * @param importOrganizations the importOrganizations to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated importOrganizations,
   * or with status {@code 400 (Bad Request)} if the importOrganizations is not valid,
   * or with status {@code 404 (Not Found)} if the importOrganizations is not found,
   * or with status {@code 500 (Internal Server Error)} if the importOrganizations couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/import-organizations/{id}", consumes = { "application/json", "application/merge-patch+json" })
  public ResponseEntity<ImportOrganizations> partialUpdateImportOrganizations(
    @PathVariable(value = "id", required = false) final Long id,
    @RequestBody ImportOrganizations importOrganizations
  ) throws URISyntaxException {
    log.debug("REST request to partial update ImportOrganizations partially : {}, {}", id, importOrganizations);
    if (importOrganizations.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, importOrganizations.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!importOrganizationsRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<ImportOrganizations> result = importOrganizationsService.partialUpdate(importOrganizations);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, importOrganizations.getId().toString())
    );
  }

  /**
   * {@code GET  /import-organizations} : get all the importOrganizations.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of importOrganizations in body.
   */
  @GetMapping("/import-organizations")
  public ResponseEntity<List<ImportOrganizations>> getAllImportOrganizations(
    @org.springdoc.api.annotations.ParameterObject Pageable pageable
  ) {
    log.debug("REST request to get a page of ImportOrganizations");
    Page<ImportOrganizations> page = importOrganizationsService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /import-organizations/:id} : get the "id" importOrganizations.
   *
   * @param id the id of the importOrganizations to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the importOrganizations, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/import-organizations/{id}")
  public ResponseEntity<ImportOrganizations> getImportOrganizations(@PathVariable Long id) {
    log.debug("REST request to get ImportOrganizations : {}", id);
    Optional<ImportOrganizations> importOrganizations = importOrganizationsService.findOne(id);
    return ResponseUtil.wrapOrNotFound(importOrganizations);
  }

  /**
   * {@code DELETE  /import-organizations/:id} : delete the "id" importOrganizations.
   *
   * @param id the id of the importOrganizations to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/import-organizations/{id}")
  public ResponseEntity<Void> deleteImportOrganizations(@PathVariable Long id) {
    log.debug("REST request to delete ImportOrganizations : {}", id);
    importOrganizationsService.delete(id);
    return ResponseEntity
      .noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
      .build();
  }
}
