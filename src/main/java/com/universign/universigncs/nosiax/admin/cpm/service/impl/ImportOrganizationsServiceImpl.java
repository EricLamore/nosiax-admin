package com.universign.universigncs.nosiax.admin.cpm.service.impl;

import com.universign.universigncs.nosiax.admin.cpm.domain.ImportOrganizations;
import com.universign.universigncs.nosiax.admin.cpm.repository.ImportOrganizationsRepository;
import com.universign.universigncs.nosiax.admin.cpm.service.ImportOrganizationsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ImportOrganizations}.
 */
@Service
@Transactional
public class ImportOrganizationsServiceImpl implements ImportOrganizationsService {

  private final Logger log = LoggerFactory.getLogger(ImportOrganizationsServiceImpl.class);

  private final ImportOrganizationsRepository importOrganizationsRepository;

  public ImportOrganizationsServiceImpl(ImportOrganizationsRepository importOrganizationsRepository) {
    this.importOrganizationsRepository = importOrganizationsRepository;
  }

  @Override
  public ImportOrganizations save(ImportOrganizations importOrganizations) {
    log.debug("Request to save ImportOrganizations : {}", importOrganizations);
    return importOrganizationsRepository.save(importOrganizations);
  }

  @Override
  public Optional<ImportOrganizations> partialUpdate(ImportOrganizations importOrganizations) {
    log.debug("Request to partially update ImportOrganizations : {}", importOrganizations);

    return importOrganizationsRepository
      .findById(importOrganizations.getId())
      .map(existingImportOrganizations -> {
        if (importOrganizations.getOrgMasterId() != null) {
          existingImportOrganizations.setOrgMasterId(importOrganizations.getOrgMasterId());
        }
        if (importOrganizations.getOrgName() != null) {
          existingImportOrganizations.setOrgName(importOrganizations.getOrgName());
        }
        if (importOrganizations.getPartenaire() != null) {
          existingImportOrganizations.setPartenaire(importOrganizations.getPartenaire());
        }
        if (importOrganizations.getProfile() != null) {
          existingImportOrganizations.setProfile(importOrganizations.getProfile());
        }
        if (importOrganizations.getLaunchCreationDate() != null) {
          existingImportOrganizations.setLaunchCreationDate(importOrganizations.getLaunchCreationDate());
        }

        return existingImportOrganizations;
      })
      .map(importOrganizationsRepository::save);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ImportOrganizations> findAll(Pageable pageable) {
    log.debug("Request to get all ImportOrganizations");
    return importOrganizationsRepository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<ImportOrganizations> findOne(Long id) {
    log.debug("Request to get ImportOrganizations : {}", id);
    return importOrganizationsRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete ImportOrganizations : {}", id);
    importOrganizationsRepository.deleteById(id);
  }
}
