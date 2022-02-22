package com.universign.universigncs.nosiax.admin.cpm.service.impl;

import com.universign.universigncs.nosiax.admin.cpm.domain.RaRecord;
import com.universign.universigncs.nosiax.admin.cpm.repository.RaRecordRepository;
import com.universign.universigncs.nosiax.admin.cpm.service.RaRecordService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RaRecord}.
 */
@Service
@Transactional
public class RaRecordServiceImpl implements RaRecordService {

  private final Logger log = LoggerFactory.getLogger(RaRecordServiceImpl.class);

  private final RaRecordRepository raRecordRepository;

  public RaRecordServiceImpl(RaRecordRepository raRecordRepository) {
    this.raRecordRepository = raRecordRepository;
  }

  @Override
  public RaRecord save(RaRecord raRecord) {
    log.debug("Request to save RaRecord : {}", raRecord);
    return raRecordRepository.save(raRecord);
  }

  @Override
  public Optional<RaRecord> partialUpdate(RaRecord raRecord) {
    log.debug("Request to partially update RaRecord : {}", raRecord);

    return raRecordRepository
      .findById(raRecord.getId())
      .map(existingRaRecord -> {
        if (raRecord.getStatus() != null) {
          existingRaRecord.setStatus(raRecord.getStatus());
        }
        if (raRecord.getIdUser() != null) {
          existingRaRecord.setIdUser(raRecord.getIdUser());
        }
        if (raRecord.getIdentifier() != null) {
          existingRaRecord.setIdentifier(raRecord.getIdentifier());
        }
        if (raRecord.getCertO() != null) {
          existingRaRecord.setCertO(raRecord.getCertO());
        }
        if (raRecord.getCommonName() != null) {
          existingRaRecord.setCommonName(raRecord.getCommonName());
        }
        if (raRecord.getZipCode() != null) {
          existingRaRecord.setZipCode(raRecord.getZipCode());
        }
        if (raRecord.getLocality() != null) {
          existingRaRecord.setLocality(raRecord.getLocality());
        }
        if (raRecord.getCountry() != null) {
          existingRaRecord.setCountry(raRecord.getCountry());
        }
        if (raRecord.getLastname() != null) {
          existingRaRecord.setLastname(raRecord.getLastname());
        }
        if (raRecord.getFirstname() != null) {
          existingRaRecord.setFirstname(raRecord.getFirstname());
        }
        if (raRecord.getEmail() != null) {
          existingRaRecord.setEmail(raRecord.getEmail());
        }
        if (raRecord.getPhone() != null) {
          existingRaRecord.setPhone(raRecord.getPhone());
        }
        if (raRecord.getUrl() != null) {
          existingRaRecord.setUrl(raRecord.getUrl());
        }
        if (raRecord.getIdTransaction() != null) {
          existingRaRecord.setIdTransaction(raRecord.getIdTransaction());
        }
        if (raRecord.getTransactionStatus() != null) {
          existingRaRecord.setTransactionStatus(raRecord.getTransactionStatus());
        }
        if (raRecord.getProfilCpm() != null) {
          existingRaRecord.setProfilCpm(raRecord.getProfilCpm());
        }
        if (raRecord.getReaso() != null) {
          existingRaRecord.setReaso(raRecord.getReaso());
        }
        if (raRecord.getSignatureDate() != null) {
          existingRaRecord.setSignatureDate(raRecord.getSignatureDate());
        }
        if (raRecord.getValidationDate() != null) {
          existingRaRecord.setValidationDate(raRecord.getValidationDate());
        }

        return existingRaRecord;
      })
      .map(raRecordRepository::save);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<RaRecord> findAll(Pageable pageable) {
    log.debug("Request to get all RaRecords");
    return raRecordRepository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<RaRecord> findOne(Long id) {
    log.debug("Request to get RaRecord : {}", id);
    return raRecordRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete RaRecord : {}", id);
    raRecordRepository.deleteById(id);
  }
}
