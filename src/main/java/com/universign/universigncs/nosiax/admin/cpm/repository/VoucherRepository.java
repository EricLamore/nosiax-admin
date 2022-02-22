package com.universign.universigncs.nosiax.admin.cpm.repository;

import com.universign.universigncs.nosiax.admin.cpm.domain.Voucher;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Voucher entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {}
