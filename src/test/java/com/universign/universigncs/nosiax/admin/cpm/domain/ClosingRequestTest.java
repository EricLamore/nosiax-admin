package com.universign.universigncs.nosiax.admin.cpm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.universign.universigncs.nosiax.admin.cpm.web.rest.TestUtil;

public class ClosingRequestTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClosingRequest.class);
        ClosingRequest closingRequest1 = new ClosingRequest();
        closingRequest1.setId(1L);
        ClosingRequest closingRequest2 = new ClosingRequest();
        closingRequest2.setId(closingRequest1.getId());
        assertThat(closingRequest1).isEqualTo(closingRequest2);
        closingRequest2.setId(2L);
        assertThat(closingRequest1).isNotEqualTo(closingRequest2);
        closingRequest1.setId(null);
        assertThat(closingRequest1).isNotEqualTo(closingRequest2);
    }
}
