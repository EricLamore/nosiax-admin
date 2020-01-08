package com.universign.universigncs.nosiax.admin.cpm.repository;

import com.universign.universigncs.nosiax.admin.cpm.domain.RaRecord;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RaRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RaRecordRepository extends JpaRepository<RaRecord, Long> {

}
