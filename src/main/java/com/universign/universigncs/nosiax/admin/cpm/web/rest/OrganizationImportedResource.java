package com.universign.universigncs.nosiax.admin.cpm.web.rest;

import com.universign.universigncs.nosiax.admin.cpm.domain.OrganizationImported;
import com.universign.universigncs.nosiax.admin.cpm.repository.OrganizationImportedRepository;
import com.universign.universigncs.nosiax.admin.cpm.service.OrganizationImportedService;
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
 * REST controller for managing {@link com.universign.universigncs.nosiax.admin.cpm.domain.OrganizationImported}.
 */
@RestController
@RequestMapping("/api")
public class OrganizationImportedResource {

  private final Logger log = LoggerFactory.getLogger(OrganizationImportedResource.class);

  private static final String ENTITY_NAME = "organizationImported";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final OrganizationImportedService organizationImportedService;

  private final OrganizationImportedRepository organizationImportedRepository;

  public OrganizationImportedResource(
    OrganizationImportedService organizationImportedService,
    OrganizationImportedRepository organizationImportedRepository
  ) {
    this.organizationImportedService = organizationImportedService;
    this.organizationImportedRepository = organizationImportedRepository;
  }

  /**
   * {@code POST  /organization-importeds} : Create a new organizationImported.
   *
   * @param organizationImported the organizationImported to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organizationImported, or with status {@code 400 (Bad Request)} if the organizationImported has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/organization-importeds")
  public ResponseEntity<OrganizationImported> createOrganizationImported(@RequestBody OrganizationImported organizationImported)
    throws URISyntaxException {
    log.debug("REST request to save OrganizationImported : {}", organizationImported);
    if (organizationImported.getId() != null) {
      throw new BadRequestAlertException("A new organizationImported cannot already have an ID", ENTITY_NAME, "idexists");
    }
    OrganizationImported result = organizationImportedService.save(organizationImported);
    return ResponseEntity
      .created(new URI("/api/organization-importeds/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /organization-importeds/:id} : Updates an existing organizationImported.
   *
   * @param id the id of the organizationImported to save.
   * @param organizationImported the organizationImported to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationImported,
   * or with status {@code 400 (Bad Request)} if the organizationImported is not valid,
   * or with status {@code 500 (Internal Server Error)} if the organizationImported couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/organization-importeds/{id}")
  public ResponseEntity<OrganizationImported> updateOrganizationImported(
    @PathVariable(value = "id", required = false) final Long id,
    @RequestBody OrganizationImported organizationImported
  ) throws URISyntaxException {
    log.debug("REST request to update OrganizationImported : {}, {}", id, organizationImported);
    if (organizationImported.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, organizationImported.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!organizationImportedRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    OrganizationImported result = organizationImportedService.save(organizationImported);
    return ResponseEntity
      .ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, organizationImported.getId().toString()))
      .body(result);
  }

  /**
   * {@code PATCH  /organization-importeds/:id} : Partial updates given fields of an existing organizationImported, field will ignore if it is null
   *
   * @param id the id of the organizationImported to save.
   * @param organizationImported the organizationImported to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationImported,
   * or with status {@code 400 (Bad Request)} if the organizationImported is not valid,
   * or with status {@code 404 (Not Found)} if the organizationImported is not found,
   * or with status {@code 500 (Internal Server Error)} if the organizationImported couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/organization-importeds/{id}", consumes = { "application/json", "application/merge-patch+json" })
  public ResponseEntity<OrganizationImported> partialUpdateOrganizationImported(
    @PathVariable(value = "id", required = false) final Long id,
    @RequestBody OrganizationImported organizationImported
  ) throws URISyntaxException {
    log.debug("REST request to partial update OrganizationImported partially : {}, {}", id, organizationImported);
    if (organizationImported.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, organizationImported.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!organizationImportedRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<OrganizationImported> result = organizationImportedService.partialUpdate(organizationImported);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, organizationImported.getId().toString())
    );
  }

  /**
   * {@code GET  /organization-importeds} : get all the organizationImporteds.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organizationImporteds in body.
   */
  @GetMapping("/organization-importeds")
  public ResponseEntity<List<OrganizationImported>> getAllOrganizationImporteds(
    @org.springdoc.api.annotations.ParameterObject Pageable pageable
  ) {
    log.debug("REST request to get a page of OrganizationImporteds");
    Page<OrganizationImported> page = organizationImportedService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /organization-importeds/:id} : get the "id" organizationImported.
   *
   * @param id the id of the organizationImported to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organizationImported, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/organization-importeds/{id}")
  public ResponseEntity<OrganizationImported> getOrganizationImported(@PathVariable Long id) {
    log.debug("REST request to get OrganizationImported : {}", id);
    Optional<OrganizationImported> organizationImported = organizationImportedService.findOne(id);
    return ResponseUtil.wrapOrNotFound(organizationImported);
  }

  /**
   * {@code DELETE  /organization-importeds/:id} : delete the "id" organizationImported.
   *
   * @param id the id of the organizationImported to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/organization-importeds/{id}")
  public ResponseEntity<Void> deleteOrganizationImported(@PathVariable Long id) {
    log.debug("REST request to delete OrganizationImported : {}", id);
    organizationImportedService.delete(id);
    return ResponseEntity
      .noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
      .build();
  }
}
