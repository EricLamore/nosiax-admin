package com.universign.universigncs.nosiax.admin.cpm.web.rest;

import com.universign.universigncs.nosiax.admin.cpm.AdminNosiaxApp;
import com.universign.universigncs.nosiax.admin.cpm.domain.ClosingRequest;
import com.universign.universigncs.nosiax.admin.cpm.repository.ClosingRequestRepository;
import com.universign.universigncs.nosiax.admin.cpm.service.ClosingRequestService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.universign.universigncs.nosiax.admin.cpm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.nosiax.admin.cpm.domain.enumeration.ClosingStatus;
/**
 * Integration tests for the {@link ClosingRequestResource} REST controller.
 */
@SpringBootTest(classes = AdminNosiaxApp.class)
public class ClosingRequestResourceIT {

    private static final Integer DEFAULT_IDX_AGENCY = 1;
    private static final Integer UPDATED_IDX_AGENCY = 2;

    private static final ClosingStatus DEFAULT_CLOSING_STATUS = ClosingStatus.CREATED;
    private static final ClosingStatus UPDATED_CLOSING_STATUS = ClosingStatus.SIGNED;

    private static final Instant DEFAULT_CLOSING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CLOSING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_REVOKE_CERTIFICATE = false;
    private static final Boolean UPDATED_REVOKE_CERTIFICATE = true;

    private static final Instant DEFAULT_REVOKE_CERTIFICATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REVOKE_CERTIFICATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_LINK_ESIGN = false;
    private static final Boolean UPDATED_LINK_ESIGN = true;

    private static final Instant DEFAULT_LINK_ESIGN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LINK_ESIGN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ANONYMIZED = false;
    private static final Boolean UPDATED_ANONYMIZED = true;

    private static final Instant DEFAULT_ANONYMIZED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ANONYMIZED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_CREATE_BILL = false;
    private static final Boolean UPDATED_CREATE_BILL = true;

    private static final Instant DEFAULT_CREATE_BILL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_BILL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_SEND_BILL = false;
    private static final Boolean UPDATED_SEND_BILL = true;

    private static final Instant DEFAULT_SEND_BILL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SEND_BILL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_TERMINATE = false;
    private static final Boolean UPDATED_TERMINATE = true;

    private static final Instant DEFAULT_TERMINATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TERMINATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ClosingRequestRepository closingRequestRepository;

    @Autowired
    private ClosingRequestService closingRequestService;

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

    private MockMvc restClosingRequestMockMvc;

    private ClosingRequest closingRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClosingRequestResource closingRequestResource = new ClosingRequestResource(closingRequestService);
        this.restClosingRequestMockMvc = MockMvcBuilders.standaloneSetup(closingRequestResource)
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
    public static ClosingRequest createEntity(EntityManager em) {
        ClosingRequest closingRequest = new ClosingRequest()
            .idxAgency(DEFAULT_IDX_AGENCY)
            .closingStatus(DEFAULT_CLOSING_STATUS)
            .closingDate(DEFAULT_CLOSING_DATE)
            .revokeCertificate(DEFAULT_REVOKE_CERTIFICATE)
            .revokeCertificateDate(DEFAULT_REVOKE_CERTIFICATE_DATE)
            .linkEsign(DEFAULT_LINK_ESIGN)
            .linkEsignDate(DEFAULT_LINK_ESIGN_DATE)
            .anonymized(DEFAULT_ANONYMIZED)
            .anonymizedDate(DEFAULT_ANONYMIZED_DATE)
            .createBill(DEFAULT_CREATE_BILL)
            .createBillDate(DEFAULT_CREATE_BILL_DATE)
            .sendBill(DEFAULT_SEND_BILL)
            .sendBillDate(DEFAULT_SEND_BILL_DATE)
            .terminate(DEFAULT_TERMINATE)
            .terminateDate(DEFAULT_TERMINATE_DATE);
        return closingRequest;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClosingRequest createUpdatedEntity(EntityManager em) {
        ClosingRequest closingRequest = new ClosingRequest()
            .idxAgency(UPDATED_IDX_AGENCY)
            .closingStatus(UPDATED_CLOSING_STATUS)
            .closingDate(UPDATED_CLOSING_DATE)
            .revokeCertificate(UPDATED_REVOKE_CERTIFICATE)
            .revokeCertificateDate(UPDATED_REVOKE_CERTIFICATE_DATE)
            .linkEsign(UPDATED_LINK_ESIGN)
            .linkEsignDate(UPDATED_LINK_ESIGN_DATE)
            .anonymized(UPDATED_ANONYMIZED)
            .anonymizedDate(UPDATED_ANONYMIZED_DATE)
            .createBill(UPDATED_CREATE_BILL)
            .createBillDate(UPDATED_CREATE_BILL_DATE)
            .sendBill(UPDATED_SEND_BILL)
            .sendBillDate(UPDATED_SEND_BILL_DATE)
            .terminate(UPDATED_TERMINATE)
            .terminateDate(UPDATED_TERMINATE_DATE);
        return closingRequest;
    }

    @BeforeEach
    public void initTest() {
        closingRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createClosingRequest() throws Exception {
        int databaseSizeBeforeCreate = closingRequestRepository.findAll().size();

        // Create the ClosingRequest
        restClosingRequestMockMvc.perform(post("/api/closing-requests")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(closingRequest)))
            .andExpect(status().isCreated());

        // Validate the ClosingRequest in the database
        List<ClosingRequest> closingRequestList = closingRequestRepository.findAll();
        assertThat(closingRequestList).hasSize(databaseSizeBeforeCreate + 1);
        ClosingRequest testClosingRequest = closingRequestList.get(closingRequestList.size() - 1);
        assertThat(testClosingRequest.getIdxAgency()).isEqualTo(DEFAULT_IDX_AGENCY);
        assertThat(testClosingRequest.getClosingStatus()).isEqualTo(DEFAULT_CLOSING_STATUS);
        assertThat(testClosingRequest.getClosingDate()).isEqualTo(DEFAULT_CLOSING_DATE);
        assertThat(testClosingRequest.isRevokeCertificate()).isEqualTo(DEFAULT_REVOKE_CERTIFICATE);
        assertThat(testClosingRequest.getRevokeCertificateDate()).isEqualTo(DEFAULT_REVOKE_CERTIFICATE_DATE);
        assertThat(testClosingRequest.isLinkEsign()).isEqualTo(DEFAULT_LINK_ESIGN);
        assertThat(testClosingRequest.getLinkEsignDate()).isEqualTo(DEFAULT_LINK_ESIGN_DATE);
        assertThat(testClosingRequest.isAnonymized()).isEqualTo(DEFAULT_ANONYMIZED);
        assertThat(testClosingRequest.getAnonymizedDate()).isEqualTo(DEFAULT_ANONYMIZED_DATE);
        assertThat(testClosingRequest.isCreateBill()).isEqualTo(DEFAULT_CREATE_BILL);
        assertThat(testClosingRequest.getCreateBillDate()).isEqualTo(DEFAULT_CREATE_BILL_DATE);
        assertThat(testClosingRequest.isSendBill()).isEqualTo(DEFAULT_SEND_BILL);
        assertThat(testClosingRequest.getSendBillDate()).isEqualTo(DEFAULT_SEND_BILL_DATE);
        assertThat(testClosingRequest.isTerminate()).isEqualTo(DEFAULT_TERMINATE);
        assertThat(testClosingRequest.getTerminateDate()).isEqualTo(DEFAULT_TERMINATE_DATE);
    }

    @Test
    @Transactional
    public void createClosingRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = closingRequestRepository.findAll().size();

        // Create the ClosingRequest with an existing ID
        closingRequest.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClosingRequestMockMvc.perform(post("/api/closing-requests")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(closingRequest)))
            .andExpect(status().isBadRequest());

        // Validate the ClosingRequest in the database
        List<ClosingRequest> closingRequestList = closingRequestRepository.findAll();
        assertThat(closingRequestList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllClosingRequests() throws Exception {
        // Initialize the database
        closingRequestRepository.saveAndFlush(closingRequest);

        // Get all the closingRequestList
        restClosingRequestMockMvc.perform(get("/api/closing-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(closingRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].idxAgency").value(hasItem(DEFAULT_IDX_AGENCY)))
            .andExpect(jsonPath("$.[*].closingStatus").value(hasItem(DEFAULT_CLOSING_STATUS.toString())))
            .andExpect(jsonPath("$.[*].closingDate").value(hasItem(DEFAULT_CLOSING_DATE.toString())))
            .andExpect(jsonPath("$.[*].revokeCertificate").value(hasItem(DEFAULT_REVOKE_CERTIFICATE.booleanValue())))
            .andExpect(jsonPath("$.[*].revokeCertificateDate").value(hasItem(DEFAULT_REVOKE_CERTIFICATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].linkEsign").value(hasItem(DEFAULT_LINK_ESIGN.booleanValue())))
            .andExpect(jsonPath("$.[*].linkEsignDate").value(hasItem(DEFAULT_LINK_ESIGN_DATE.toString())))
            .andExpect(jsonPath("$.[*].anonymized").value(hasItem(DEFAULT_ANONYMIZED.booleanValue())))
            .andExpect(jsonPath("$.[*].anonymizedDate").value(hasItem(DEFAULT_ANONYMIZED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createBill").value(hasItem(DEFAULT_CREATE_BILL.booleanValue())))
            .andExpect(jsonPath("$.[*].createBillDate").value(hasItem(DEFAULT_CREATE_BILL_DATE.toString())))
            .andExpect(jsonPath("$.[*].sendBill").value(hasItem(DEFAULT_SEND_BILL.booleanValue())))
            .andExpect(jsonPath("$.[*].sendBillDate").value(hasItem(DEFAULT_SEND_BILL_DATE.toString())))
            .andExpect(jsonPath("$.[*].terminate").value(hasItem(DEFAULT_TERMINATE.booleanValue())))
            .andExpect(jsonPath("$.[*].terminateDate").value(hasItem(DEFAULT_TERMINATE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getClosingRequest() throws Exception {
        // Initialize the database
        closingRequestRepository.saveAndFlush(closingRequest);

        // Get the closingRequest
        restClosingRequestMockMvc.perform(get("/api/closing-requests/{id}", closingRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(closingRequest.getId().intValue()))
            .andExpect(jsonPath("$.idxAgency").value(DEFAULT_IDX_AGENCY))
            .andExpect(jsonPath("$.closingStatus").value(DEFAULT_CLOSING_STATUS.toString()))
            .andExpect(jsonPath("$.closingDate").value(DEFAULT_CLOSING_DATE.toString()))
            .andExpect(jsonPath("$.revokeCertificate").value(DEFAULT_REVOKE_CERTIFICATE.booleanValue()))
            .andExpect(jsonPath("$.revokeCertificateDate").value(DEFAULT_REVOKE_CERTIFICATE_DATE.toString()))
            .andExpect(jsonPath("$.linkEsign").value(DEFAULT_LINK_ESIGN.booleanValue()))
            .andExpect(jsonPath("$.linkEsignDate").value(DEFAULT_LINK_ESIGN_DATE.toString()))
            .andExpect(jsonPath("$.anonymized").value(DEFAULT_ANONYMIZED.booleanValue()))
            .andExpect(jsonPath("$.anonymizedDate").value(DEFAULT_ANONYMIZED_DATE.toString()))
            .andExpect(jsonPath("$.createBill").value(DEFAULT_CREATE_BILL.booleanValue()))
            .andExpect(jsonPath("$.createBillDate").value(DEFAULT_CREATE_BILL_DATE.toString()))
            .andExpect(jsonPath("$.sendBill").value(DEFAULT_SEND_BILL.booleanValue()))
            .andExpect(jsonPath("$.sendBillDate").value(DEFAULT_SEND_BILL_DATE.toString()))
            .andExpect(jsonPath("$.terminate").value(DEFAULT_TERMINATE.booleanValue()))
            .andExpect(jsonPath("$.terminateDate").value(DEFAULT_TERMINATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClosingRequest() throws Exception {
        // Get the closingRequest
        restClosingRequestMockMvc.perform(get("/api/closing-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClosingRequest() throws Exception {
        // Initialize the database
        closingRequestService.save(closingRequest);

        int databaseSizeBeforeUpdate = closingRequestRepository.findAll().size();

        // Update the closingRequest
        ClosingRequest updatedClosingRequest = closingRequestRepository.findById(closingRequest.getId()).get();
        // Disconnect from session so that the updates on updatedClosingRequest are not directly saved in db
        em.detach(updatedClosingRequest);
        updatedClosingRequest
            .idxAgency(UPDATED_IDX_AGENCY)
            .closingStatus(UPDATED_CLOSING_STATUS)
            .closingDate(UPDATED_CLOSING_DATE)
            .revokeCertificate(UPDATED_REVOKE_CERTIFICATE)
            .revokeCertificateDate(UPDATED_REVOKE_CERTIFICATE_DATE)
            .linkEsign(UPDATED_LINK_ESIGN)
            .linkEsignDate(UPDATED_LINK_ESIGN_DATE)
            .anonymized(UPDATED_ANONYMIZED)
            .anonymizedDate(UPDATED_ANONYMIZED_DATE)
            .createBill(UPDATED_CREATE_BILL)
            .createBillDate(UPDATED_CREATE_BILL_DATE)
            .sendBill(UPDATED_SEND_BILL)
            .sendBillDate(UPDATED_SEND_BILL_DATE)
            .terminate(UPDATED_TERMINATE)
            .terminateDate(UPDATED_TERMINATE_DATE);

        restClosingRequestMockMvc.perform(put("/api/closing-requests")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedClosingRequest)))
            .andExpect(status().isOk());

        // Validate the ClosingRequest in the database
        List<ClosingRequest> closingRequestList = closingRequestRepository.findAll();
        assertThat(closingRequestList).hasSize(databaseSizeBeforeUpdate);
        ClosingRequest testClosingRequest = closingRequestList.get(closingRequestList.size() - 1);
        assertThat(testClosingRequest.getIdxAgency()).isEqualTo(UPDATED_IDX_AGENCY);
        assertThat(testClosingRequest.getClosingStatus()).isEqualTo(UPDATED_CLOSING_STATUS);
        assertThat(testClosingRequest.getClosingDate()).isEqualTo(UPDATED_CLOSING_DATE);
        assertThat(testClosingRequest.isRevokeCertificate()).isEqualTo(UPDATED_REVOKE_CERTIFICATE);
        assertThat(testClosingRequest.getRevokeCertificateDate()).isEqualTo(UPDATED_REVOKE_CERTIFICATE_DATE);
        assertThat(testClosingRequest.isLinkEsign()).isEqualTo(UPDATED_LINK_ESIGN);
        assertThat(testClosingRequest.getLinkEsignDate()).isEqualTo(UPDATED_LINK_ESIGN_DATE);
        assertThat(testClosingRequest.isAnonymized()).isEqualTo(UPDATED_ANONYMIZED);
        assertThat(testClosingRequest.getAnonymizedDate()).isEqualTo(UPDATED_ANONYMIZED_DATE);
        assertThat(testClosingRequest.isCreateBill()).isEqualTo(UPDATED_CREATE_BILL);
        assertThat(testClosingRequest.getCreateBillDate()).isEqualTo(UPDATED_CREATE_BILL_DATE);
        assertThat(testClosingRequest.isSendBill()).isEqualTo(UPDATED_SEND_BILL);
        assertThat(testClosingRequest.getSendBillDate()).isEqualTo(UPDATED_SEND_BILL_DATE);
        assertThat(testClosingRequest.isTerminate()).isEqualTo(UPDATED_TERMINATE);
        assertThat(testClosingRequest.getTerminateDate()).isEqualTo(UPDATED_TERMINATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingClosingRequest() throws Exception {
        int databaseSizeBeforeUpdate = closingRequestRepository.findAll().size();

        // Create the ClosingRequest

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClosingRequestMockMvc.perform(put("/api/closing-requests")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(closingRequest)))
            .andExpect(status().isBadRequest());

        // Validate the ClosingRequest in the database
        List<ClosingRequest> closingRequestList = closingRequestRepository.findAll();
        assertThat(closingRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClosingRequest() throws Exception {
        // Initialize the database
        closingRequestService.save(closingRequest);

        int databaseSizeBeforeDelete = closingRequestRepository.findAll().size();

        // Delete the closingRequest
        restClosingRequestMockMvc.perform(delete("/api/closing-requests/{id}", closingRequest.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClosingRequest> closingRequestList = closingRequestRepository.findAll();
        assertThat(closingRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
