package com.universign.universigncs.nosiax.admin.cpm.repository;

import com.universign.universigncs.nosiax.admin.cpm.domain.AdditionalKeys;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AdditionalKeys entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdditionalKeysRepository extends JpaRepository<AdditionalKeys, Long> {

}
