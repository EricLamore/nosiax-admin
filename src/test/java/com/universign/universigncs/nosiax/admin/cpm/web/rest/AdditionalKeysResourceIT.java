package com.universign.universigncs.nosiax.admin.cpm.web.rest;

import com.universign.universigncs.nosiax.admin.cpm.AdminNosiaxApp;
import com.universign.universigncs.nosiax.admin.cpm.domain.AdditionalKeys;
import com.universign.universigncs.nosiax.admin.cpm.repository.AdditionalKeysRepository;
import com.universign.universigncs.nosiax.admin.cpm.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.universign.universigncs.nosiax.admin.cpm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AdditionalKeysResource} REST controller.
 */
@SpringBootTest(classes = AdminNosiaxApp.class)
public class AdditionalKeysResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private AdditionalKeysRepository additionalKeysRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAdditionalKeysMockMvc;

    private AdditionalKeys additionalKeys;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdditionalKeysResource additionalKeysResource = new AdditionalKeysResource(additionalKeysRepository);
        this.restAdditionalKeysMockMvc = MockMvcBuilders.standaloneSetup(additionalKeysResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdditionalKeys createEntity(EntityManager em) {
        AdditionalKeys additionalKeys = new AdditionalKeys()
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE);
        return additionalKeys;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdditionalKeys createUpdatedEntity(EntityManager em) {
        AdditionalKeys additionalKeys = new AdditionalKeys()
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);
        return additionalKeys;
    }

    @BeforeEach
    public void initTest() {
        additionalKeys = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdditionalKeys() throws Exception {
        int databaseSizeBeforeCreate = additionalKeysRepository.findAll().size();

        // Create the AdditionalKeys
        restAdditionalKeysMockMvc.perform(post("/api/additional-keys")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(additionalKeys)))
            .andExpect(status().isCreated());

        // Validate the AdditionalKeys in the database
        List<AdditionalKeys> additionalKeysList = additionalKeysRepository.findAll();
        assertThat(additionalKeysList).hasSize(databaseSizeBeforeCreate + 1);
        AdditionalKeys testAdditionalKeys = additionalKeysList.get(additionalKeysList.size() - 1);
        assertThat(testAdditionalKeys.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testAdditionalKeys.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createAdditionalKeysWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = additionalKeysRepository.findAll().size();

        // Create the AdditionalKeys with an existing ID
        additionalKeys.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdditionalKeysMockMvc.perform(post("/api/additional-keys")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(additionalKeys)))
            .andExpect(status().isBadRequest());

        // Validate the AdditionalKeys in the database
        List<AdditionalKeys> additionalKeysList = additionalKeysRepository.findAll();
        assertThat(additionalKeysList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = additionalKeysRepository.findAll().size();
        // set the field null
        additionalKeys.setKey(null);

        // Create the AdditionalKeys, which fails.

        restAdditionalKeysMockMvc.perform(post("/api/additional-keys")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(additionalKeys)))
            .andExpect(status().isBadRequest());

        List<AdditionalKeys> additionalKeysList = additionalKeysRepository.findAll();
        assertThat(additionalKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = additionalKeysRepository.findAll().size();
        // set the field null
        additionalKeys.setValue(null);

        // Create the AdditionalKeys, which fails.

        restAdditionalKeysMockMvc.perform(post("/api/additional-keys")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(additionalKeys)))
            .andExpect(status().isBadRequest());

        List<AdditionalKeys> additionalKeysList = additionalKeysRepository.findAll();
        assertThat(additionalKeysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdditionalKeys() throws Exception {
        // Initialize the database
        additionalKeysRepository.saveAndFlush(additionalKeys);

        // Get all the additionalKeysList
        restAdditionalKeysMockMvc.perform(get("/api/additional-keys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(additionalKeys.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getAdditionalKeys() throws Exception {
        // Initialize the database
        additionalKeysRepository.saveAndFlush(additionalKeys);

        // Get the additionalKeys
        restAdditionalKeysMockMvc.perform(get("/api/additional-keys/{id}", additionalKeys.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(additionalKeys.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingAdditionalKeys() throws Exception {
        // Get the additionalKeys
        restAdditionalKeysMockMvc.perform(get("/api/additional-keys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdditionalKeys() throws Exception {
        // Initialize the database
        additionalKeysRepository.saveAndFlush(additionalKeys);

        int databaseSizeBeforeUpdate = additionalKeysRepository.findAll().size();

        // Update the additionalKeys
        AdditionalKeys updatedAdditionalKeys = additionalKeysRepository.findById(additionalKeys.getId()).get();
        // Disconnect from session so that the updates on updatedAdditionalKeys are not directly saved in db
        em.detach(updatedAdditionalKeys);
        updatedAdditionalKeys
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);

        restAdditionalKeysMockMvc.perform(put("/api/additional-keys")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAdditionalKeys)))
            .andExpect(status().isOk());

        // Validate the AdditionalKeys in the database
        List<AdditionalKeys> additionalKeysList = additionalKeysRepository.findAll();
        assertThat(additionalKeysList).hasSize(databaseSizeBeforeUpdate);
        AdditionalKeys testAdditionalKeys = additionalKeysList.get(additionalKeysList.size() - 1);
        assertThat(testAdditionalKeys.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testAdditionalKeys.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingAdditionalKeys() throws Exception {
        int databaseSizeBeforeUpdate = additionalKeysRepository.findAll().size();

        // Create the AdditionalKeys

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdditionalKeysMockMvc.perform(put("/api/additional-keys")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(additionalKeys)))
            .andExpect(status().isBadRequest());

        // Validate the AdditionalKeys in the database
        List<AdditionalKeys> additionalKeysList = additionalKeysRepository.findAll();
        assertThat(additionalKeysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdditionalKeys() throws Exception {
        // Initialize the database
        additionalKeysRepository.saveAndFlush(additionalKeys);

        int databaseSizeBeforeDelete = additionalKeysRepository.findAll().size();

        // Delete the additionalKeys
        restAdditionalKeysMockMvc.perform(delete("/api/additional-keys/{id}", additionalKeys.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdditionalKeys> additionalKeysList = additionalKeysRepository.findAll();
        assertThat(additionalKeysList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
