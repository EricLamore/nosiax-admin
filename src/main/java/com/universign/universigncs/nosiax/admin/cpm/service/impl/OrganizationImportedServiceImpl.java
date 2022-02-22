package com.universign.universigncs.nosiax.admin.cpm.service.impl;

import com.universign.universigncs.nosiax.admin.cpm.domain.OrganizationImported;
import com.universign.universigncs.nosiax.admin.cpm.repository.OrganizationImportedRepository;
import com.universign.universigncs.nosiax.admin.cpm.service.OrganizationImportedService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrganizationImported}.
 */
@Service
@Transactional
public class OrganizationImportedServiceImpl implements OrganizationImportedService {

  private final Logger log = LoggerFactory.getLogger(OrganizationImportedServiceImpl.class);

  private final OrganizationImportedRepository organizationImportedRepository;

  public OrganizationImportedServiceImpl(OrganizationImportedRepository organizationImportedRepository) {
    this.organizationImportedRepository = organizationImportedRepository;
  }

  @Override
  public OrganizationImported save(OrganizationImported organizationImported) {
    log.debug("Request to save OrganizationImported : {}", organizationImported);
    return organizationImportedRepository.save(organizationImported);
  }

  @Override
  public Optional<OrganizationImported> partialUpdate(OrganizationImported organizationImported) {
    log.debug("Request to partially update OrganizationImported : {}", organizationImported);

    return organizationImportedRepository
      .findById(organizationImported.getId())
      .map(existingOrganizationImported -> {
        if (organizationImported.getClient() != null) {
          existingOrganizationImported.setClient(organizationImported.getClient());
        }
        if (organizationImported.getDisplayname() != null) {
          existingOrganizationImported.setDisplayname(organizationImported.getDisplayname());
        }
        if (organizationImported.getLogo() != null) {
          existingOrganizationImported.setLogo(organizationImported.getLogo());
        }
        if (organizationImported.getProfileName() != null) {
          existingOrganizationImported.setProfileName(organizationImported.getProfileName());
        }
        if (organizationImported.getConsoSig() != null) {
          existingOrganizationImported.setConsoSig(organizationImported.getConsoSig());
        }
        if (organizationImported.getConsoTimes() != null) {
          existingOrganizationImported.setConsoTimes(organizationImported.getConsoTimes());
        }
        if (organizationImported.getConsoSeal() != null) {
          existingOrganizationImported.setConsoSeal(organizationImported.getConsoSeal());
        }
        if (organizationImported.getTechnicalAccountCreation() != null) {
          existingOrganizationImported.setTechnicalAccountCreation(organizationImported.getTechnicalAccountCreation());
        }
        if (organizationImported.getIsTechnicalAccountAdmin() != null) {
          existingOrganizationImported.setIsTechnicalAccountAdmin(organizationImported.getIsTechnicalAccountAdmin());
        }
        if (organizationImported.getIsTechnicalAccount() != null) {
          existingOrganizationImported.setIsTechnicalAccount(organizationImported.getIsTechnicalAccount());
        }
        if (organizationImported.getIsOperator() != null) {
          existingOrganizationImported.setIsOperator(organizationImported.getIsOperator());
        }
        if (organizationImported.getTechnicalAccountFirstname() != null) {
          existingOrganizationImported.setTechnicalAccountFirstname(organizationImported.getTechnicalAccountFirstname());
        }
        if (organizationImported.getTechnicalAccountLastname() != null) {
          existingOrganizationImported.setTechnicalAccountLastname(organizationImported.getTechnicalAccountLastname());
        }
        if (organizationImported.getTechnicalAccountMail() != null) {
          existingOrganizationImported.setTechnicalAccountMail(organizationImported.getTechnicalAccountMail());
        }
        if (organizationImported.getLanguage() != null) {
          existingOrganizationImported.setLanguage(organizationImported.getLanguage());
        }
        if (organizationImported.getOrgCreated() != null) {
          existingOrganizationImported.setOrgCreated(organizationImported.getOrgCreated());
        }
        if (organizationImported.getTechnicalAccountCreated() != null) {
          existingOrganizationImported.setTechnicalAccountCreated(organizationImported.getTechnicalAccountCreated());
        }
        if (organizationImported.getConsoCreated() != null) {
          existingOrganizationImported.setConsoCreated(organizationImported.getConsoCreated());
        }
        if (organizationImported.getCreateDate() != null) {
          existingOrganizationImported.setCreateDate(organizationImported.getCreateDate());
        }

        return existingOrganizationImported;
      })
      .map(organizationImportedRepository::save);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<OrganizationImported> findAll(Pageable pageable) {
    log.debug("Request to get all OrganizationImporteds");
    return organizationImportedRepository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<OrganizationImported> findOne(Long id) {
    log.debug("Request to get OrganizationImported : {}", id);
    return organizationImportedRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete OrganizationImported : {}", id);
    organizationImportedRepository.deleteById(id);
  }
}
