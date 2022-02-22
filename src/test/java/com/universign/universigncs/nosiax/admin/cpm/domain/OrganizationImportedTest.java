package com.universign.universigncs.nosiax.admin.cpm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.universign.universigncs.nosiax.admin.cpm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganizationImportedTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(OrganizationImported.class);
    OrganizationImported organizationImported1 = new OrganizationImported();
    organizationImported1.setId(1L);
    OrganizationImported organizationImported2 = new OrganizationImported();
    organizationImported2.setId(organizationImported1.getId());
    assertThat(organizationImported1).isEqualTo(organizationImported2);
    organizationImported2.setId(2L);
    assertThat(organizationImported1).isNotEqualTo(organizationImported2);
    organizationImported1.setId(null);
    assertThat(organizationImported1).isNotEqualTo(organizationImported2);
  }
}
