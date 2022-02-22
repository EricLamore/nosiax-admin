package com.universign.universigncs.nosiax.admin.cpm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.universign.universigncs.nosiax.admin.cpm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RaRecordTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(RaRecord.class);
    RaRecord raRecord1 = new RaRecord();
    raRecord1.setId(1L);
    RaRecord raRecord2 = new RaRecord();
    raRecord2.setId(raRecord1.getId());
    assertThat(raRecord1).isEqualTo(raRecord2);
    raRecord2.setId(2L);
    assertThat(raRecord1).isNotEqualTo(raRecord2);
    raRecord1.setId(null);
    assertThat(raRecord1).isNotEqualTo(raRecord2);
  }
}
