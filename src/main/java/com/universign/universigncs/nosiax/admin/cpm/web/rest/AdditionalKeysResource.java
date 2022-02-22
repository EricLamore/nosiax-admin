package com.universign.universigncs.nosiax.admin.cpm.web.rest;

import com.universign.universigncs.nosiax.admin.cpm.domain.AdditionalKeys;
import com.universign.universigncs.nosiax.admin.cpm.repository.AdditionalKeysRepository;
import com.universign.universigncs.nosiax.admin.cpm.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.universign.universigncs.nosiax.admin.cpm.domain.AdditionalKeys}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AdditionalKeysResource {

  private final Logger log = LoggerFactory.getLogger(AdditionalKeysResource.class);

  private static final String ENTITY_NAME = "additionalKeys";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final AdditionalKeysRepository additionalKeysRepository;

  public AdditionalKeysResource(AdditionalKeysRepository additionalKeysRepository) {
    this.additionalKeysRepository = additionalKeysRepository;
  }

  /**
   * {@code POST  /additional-keys} : Create a new additionalKeys.
   *
   * @param additionalKeys the additionalKeys to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new additionalKeys, or with status {@code 400 (Bad Request)} if the additionalKeys has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/additional-keys")
  public ResponseEntity<AdditionalKeys> createAdditionalKeys(@Valid @RequestBody AdditionalKeys additionalKeys) throws URISyntaxException {
    log.debug("REST request to save AdditionalKeys : {}", additionalKeys);
    if (additionalKeys.getId() != null) {
      throw new BadRequestAlertException("A new additionalKeys cannot already have an ID", ENTITY_NAME, "idexists");
    }
    AdditionalKeys result = additionalKeysRepository.save(additionalKeys);
    return ResponseEntity
      .created(new URI("/api/additional-keys/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /additional-keys/:id} : Updates an existing additionalKeys.
   *
   * @param id the id of the additionalKeys to save.
   * @param additionalKeys the additionalKeys to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated additionalKeys,
   * or with status {@code 400 (Bad Request)} if the additionalKeys is not valid,
   * or with status {@code 500 (Internal Server Error)} if the additionalKeys couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/additional-keys/{id}")
  public ResponseEntity<AdditionalKeys> updateAdditionalKeys(
    @PathVariable(value = "id", required = false) final Long id,
    @Valid @RequestBody AdditionalKeys additionalKeys
  ) throws URISyntaxException {
    log.debug("REST request to update AdditionalKeys : {}, {}", id, additionalKeys);
    if (additionalKeys.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, additionalKeys.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!additionalKeysRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    AdditionalKeys result = additionalKeysRepository.save(additionalKeys);
    return ResponseEntity
      .ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, additionalKeys.getId().toString()))
      .body(result);
  }

  /**
   * {@code PATCH  /additional-keys/:id} : Partial updates given fields of an existing additionalKeys, field will ignore if it is null
   *
   * @param id the id of the additionalKeys to save.
   * @param additionalKeys the additionalKeys to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated additionalKeys,
   * or with status {@code 400 (Bad Request)} if the additionalKeys is not valid,
   * or with status {@code 404 (Not Found)} if the additionalKeys is not found,
   * or with status {@code 500 (Internal Server Error)} if the additionalKeys couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/additional-keys/{id}", consumes = { "application/json", "application/merge-patch+json" })
  public ResponseEntity<AdditionalKeys> partialUpdateAdditionalKeys(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody AdditionalKeys additionalKeys
  ) throws URISyntaxException {
    log.debug("REST request to partial update AdditionalKeys partially : {}, {}", id, additionalKeys);
    if (additionalKeys.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, additionalKeys.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!additionalKeysRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<AdditionalKeys> result = additionalKeysRepository
      .findById(additionalKeys.getId())
      .map(existingAdditionalKeys -> {
        if (additionalKeys.getKey() != null) {
          existingAdditionalKeys.setKey(additionalKeys.getKey());
        }
        if (additionalKeys.getValue() != null) {
          existingAdditionalKeys.setValue(additionalKeys.getValue());
        }

        return existingAdditionalKeys;
      })
      .map(additionalKeysRepository::save);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, additionalKeys.getId().toString())
    );
  }

  /**
   * {@code GET  /additional-keys} : get all the additionalKeys.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of additionalKeys in body.
   */
  @GetMapping("/additional-keys")
  public List<AdditionalKeys> getAllAdditionalKeys() {
    log.debug("REST request to get all AdditionalKeys");
    return additionalKeysRepository.findAll();
  }

  /**
   * {@code GET  /additional-keys/:id} : get the "id" additionalKeys.
   *
   * @param id the id of the additionalKeys to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the additionalKeys, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/additional-keys/{id}")
  public ResponseEntity<AdditionalKeys> getAdditionalKeys(@PathVariable Long id) {
    log.debug("REST request to get AdditionalKeys : {}", id);
    Optional<AdditionalKeys> additionalKeys = additionalKeysRepository.findById(id);
    return ResponseUtil.wrapOrNotFound(additionalKeys);
  }

  /**
   * {@code DELETE  /additional-keys/:id} : delete the "id" additionalKeys.
   *
   * @param id the id of the additionalKeys to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/additional-keys/{id}")
  public ResponseEntity<Void> deleteAdditionalKeys(@PathVariable Long id) {
    log.debug("REST request to delete AdditionalKeys : {}", id);
    additionalKeysRepository.deleteById(id);
    return ResponseEntity
      .noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
      .build();
  }
}
