package com.universign.universigncs.nosiax.admin.cpm.repository;

import com.universign.universigncs.nosiax.admin.cpm.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
