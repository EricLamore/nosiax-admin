package com.universign.universigncs.nosiax.admin.cpm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.universign.universigncs.nosiax.admin.cpm.web.rest.TestUtil;

public class AdditionalKeysTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdditionalKeys.class);
        AdditionalKeys additionalKeys1 = new AdditionalKeys();
        additionalKeys1.setId(1L);
        AdditionalKeys additionalKeys2 = new AdditionalKeys();
        additionalKeys2.setId(additionalKeys1.getId());
        assertThat(additionalKeys1).isEqualTo(additionalKeys2);
        additionalKeys2.setId(2L);
        assertThat(additionalKeys1).isNotEqualTo(additionalKeys2);
        additionalKeys1.setId(null);
        assertThat(additionalKeys1).isNotEqualTo(additionalKeys2);
    }
}
