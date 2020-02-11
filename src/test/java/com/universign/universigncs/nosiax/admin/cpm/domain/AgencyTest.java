package com.universign.universigncs.nosiax.admin.cpm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.universign.universigncs.nosiax.admin.cpm.web.rest.TestUtil;

public class AgencyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agency.class);
        Agency agency1 = new Agency();
        agency1.setId(1L);
        Agency agency2 = new Agency();
        agency2.setId(agency1.getId());
        assertThat(agency1).isEqualTo(agency2);
        agency2.setId(2L);
        assertThat(agency1).isNotEqualTo(agency2);
        agency1.setId(null);
        assertThat(agency1).isNotEqualTo(agency2);
    }
}
