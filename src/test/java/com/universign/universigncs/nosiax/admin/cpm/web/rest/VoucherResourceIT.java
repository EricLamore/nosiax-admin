package com.universign.universigncs.nosiax.admin.cpm.web.rest;

import com.universign.universigncs.nosiax.admin.cpm.AdminNosiaxApp;
import com.universign.universigncs.nosiax.admin.cpm.domain.Voucher;
import com.universign.universigncs.nosiax.admin.cpm.repository.VoucherRepository;
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
 * Integration tests for the {@link VoucherResource} REST controller.
 */
@SpringBootTest(classes = AdminNosiaxApp.class)
public class VoucherResourceIT {

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    @Autowired
    private VoucherRepository voucherRepository;

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

    private MockMvc restVoucherMockMvc;

    private Voucher voucher;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VoucherResource voucherResource = new VoucherResource(voucherRepository);
        this.restVoucherMockMvc = MockMvcBuilders.standaloneSetup(voucherResource)
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
    public static Voucher createEntity(EntityManager em) {
        Voucher voucher = new Voucher()
            .fileName(DEFAULT_FILE_NAME)
            .key(DEFAULT_KEY);
        return voucher;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voucher createUpdatedEntity(EntityManager em) {
        Voucher voucher = new Voucher()
            .fileName(UPDATED_FILE_NAME)
            .key(UPDATED_KEY);
        return voucher;
    }

    @BeforeEach
    public void initTest() {
        voucher = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoucher() throws Exception {
        int databaseSizeBeforeCreate = voucherRepository.findAll().size();

        // Create the Voucher
        restVoucherMockMvc.perform(post("/api/vouchers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voucher)))
            .andExpect(status().isCreated());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeCreate + 1);
        Voucher testVoucher = voucherList.get(voucherList.size() - 1);
        assertThat(testVoucher.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testVoucher.getKey()).isEqualTo(DEFAULT_KEY);
    }

    @Test
    @Transactional
    public void createVoucherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voucherRepository.findAll().size();

        // Create the Voucher with an existing ID
        voucher.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoucherMockMvc.perform(post("/api/vouchers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voucher)))
            .andExpect(status().isBadRequest());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = voucherRepository.findAll().size();
        // set the field null
        voucher.setFileName(null);

        // Create the Voucher, which fails.

        restVoucherMockMvc.perform(post("/api/vouchers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voucher)))
            .andExpect(status().isBadRequest());

        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = voucherRepository.findAll().size();
        // set the field null
        voucher.setKey(null);

        // Create the Voucher, which fails.

        restVoucherMockMvc.perform(post("/api/vouchers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voucher)))
            .andExpect(status().isBadRequest());

        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVouchers() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList
        restVoucherMockMvc.perform(get("/api/vouchers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)));
    }
    
    @Test
    @Transactional
    public void getVoucher() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get the voucher
        restVoucherMockMvc.perform(get("/api/vouchers/{id}", voucher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voucher.getId().intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY));
    }

    @Test
    @Transactional
    public void getNonExistingVoucher() throws Exception {
        // Get the voucher
        restVoucherMockMvc.perform(get("/api/vouchers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoucher() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        int databaseSizeBeforeUpdate = voucherRepository.findAll().size();

        // Update the voucher
        Voucher updatedVoucher = voucherRepository.findById(voucher.getId()).get();
        // Disconnect from session so that the updates on updatedVoucher are not directly saved in db
        em.detach(updatedVoucher);
        updatedVoucher
            .fileName(UPDATED_FILE_NAME)
            .key(UPDATED_KEY);

        restVoucherMockMvc.perform(put("/api/vouchers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVoucher)))
            .andExpect(status().isOk());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
        Voucher testVoucher = voucherList.get(voucherList.size() - 1);
        assertThat(testVoucher.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testVoucher.getKey()).isEqualTo(UPDATED_KEY);
    }

    @Test
    @Transactional
    public void updateNonExistingVoucher() throws Exception {
        int databaseSizeBeforeUpdate = voucherRepository.findAll().size();

        // Create the Voucher

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherMockMvc.perform(put("/api/vouchers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voucher)))
            .andExpect(status().isBadRequest());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVoucher() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        int databaseSizeBeforeDelete = voucherRepository.findAll().size();

        // Delete the voucher
        restVoucherMockMvc.perform(delete("/api/vouchers/{id}", voucher.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
