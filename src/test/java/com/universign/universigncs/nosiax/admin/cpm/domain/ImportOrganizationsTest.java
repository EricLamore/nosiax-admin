package com.universign.universigncs.nosiax.admin.cpm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.universign.universigncs.nosiax.admin.cpm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ImportOrganizationsTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(ImportOrganizations.class);
    ImportOrganizations importOrganizations1 = new ImportOrganizations();
    importOrganizations1.setId(1L);
    ImportOrganizations importOrganizations2 = new ImportOrganizations();
    importOrganizations2.setId(importOrganizations1.getId());
    assertThat(importOrganizations1).isEqualTo(importOrganizations2);
    importOrganizations2.setId(2L);
    assertThat(importOrganizations1).isNotEqualTo(importOrganizations2);
    importOrganizations1.setId(null);
    assertThat(importOrganizations1).isNotEqualTo(importOrganizations2);
  }
}
