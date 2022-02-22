package com.universign.universigncs.nosiax.admin.cpm.web.rest;

import static com.universign.universigncs.nosiax.admin.cpm.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.nosiax.admin.cpm.IntegrationTest;
import com.universign.universigncs.nosiax.admin.cpm.domain.RaRecord;
import com.universign.universigncs.nosiax.admin.cpm.domain.enumeration.Status;
import com.universign.universigncs.nosiax.admin.cpm.repository.RaRecordRepository;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RaRecordResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RaRecordResourceIT {

  private static final Status DEFAULT_STATUS = Status.DRAFT;
  private static final Status UPDATED_STATUS = Status.TO_SIGN;

  private static final String DEFAULT_ID_USER = "AAAAAAAAAA";
  private static final String UPDATED_ID_USER = "BBBBBBBBBB";

  private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
  private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

  private static final String DEFAULT_CERT_O = "AAAAAAAAAA";
  private static final String UPDATED_CERT_O = "BBBBBBBBBB";

  private static final String DEFAULT_COMMON_NAME = "AAAAAAAAAA";
  private static final String UPDATED_COMMON_NAME = "BBBBBBBBBB";

  private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
  private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

  private static final String DEFAULT_LOCALITY = "AAAAAAAAAA";
  private static final String UPDATED_LOCALITY = "BBBBBBBBBB";

  private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
  private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

  private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
  private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

  private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
  private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

  private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
  private static final String UPDATED_EMAIL = "BBBBBBBBBB";

  private static final String DEFAULT_PHONE = "AAAAAAAAAA";
  private static final String UPDATED_PHONE = "BBBBBBBBBB";

  private static final String DEFAULT_URL = "AAAAAAAAAA";
  private static final String UPDATED_URL = "BBBBBBBBBB";

  private static final String DEFAULT_ID_TRANSACTION = "AAAAAAAAAA";
  private static final String UPDATED_ID_TRANSACTION = "BBBBBBBBBB";

  private static final String DEFAULT_TRANSACTION_STATUS = "AAAAAAAAAA";
  private static final String UPDATED_TRANSACTION_STATUS = "BBBBBBBBBB";

  private static final String DEFAULT_PROFIL_CPM = "AAAAAAAAAA";
  private static final String UPDATED_PROFIL_CPM = "BBBBBBBBBB";

  private static final String DEFAULT_REASO = "AAAAAAAAAA";
  private static final String UPDATED_REASO = "BBBBBBBBBB";

  private static final ZonedDateTime DEFAULT_SIGNATURE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
  private static final ZonedDateTime UPDATED_SIGNATURE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

  private static final ZonedDateTime DEFAULT_VALIDATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
  private static final ZonedDateTime UPDATED_VALIDATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

  private static final String ENTITY_API_URL = "/api/ra-records";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private RaRecordRepository raRecordRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restRaRecordMockMvc;

  private RaRecord raRecord;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static RaRecord createEntity(EntityManager em) {
    RaRecord raRecord = new RaRecord()
      .status(DEFAULT_STATUS)
      .idUser(DEFAULT_ID_USER)
      .identifier(DEFAULT_IDENTIFIER)
      .certO(DEFAULT_CERT_O)
      .commonName(DEFAULT_COMMON_NAME)
      .zipCode(DEFAULT_ZIP_CODE)
      .locality(DEFAULT_LOCALITY)
      .country(DEFAULT_COUNTRY)
      .lastname(DEFAULT_LASTNAME)
      .firstname(DEFAULT_FIRSTNAME)
      .email(DEFAULT_EMAIL)
      .phone(DEFAULT_PHONE)
      .url(DEFAULT_URL)
      .idTransaction(DEFAULT_ID_TRANSACTION)
      .transactionStatus(DEFAULT_TRANSACTION_STATUS)
      .profilCpm(DEFAULT_PROFIL_CPM)
      .reaso(DEFAULT_REASO)
      .signatureDate(DEFAULT_SIGNATURE_DATE)
      .validationDate(DEFAULT_VALIDATION_DATE);
    return raRecord;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static RaRecord createUpdatedEntity(EntityManager em) {
    RaRecord raRecord = new RaRecord()
      .status(UPDATED_STATUS)
      .idUser(UPDATED_ID_USER)
      .identifier(UPDATED_IDENTIFIER)
      .certO(UPDATED_CERT_O)
      .commonName(UPDATED_COMMON_NAME)
      .zipCode(UPDATED_ZIP_CODE)
      .locality(UPDATED_LOCALITY)
      .country(UPDATED_COUNTRY)
      .lastname(UPDATED_LASTNAME)
      .firstname(UPDATED_FIRSTNAME)
      .email(UPDATED_EMAIL)
      .phone(UPDATED_PHONE)
      .url(UPDATED_URL)
      .idTransaction(UPDATED_ID_TRANSACTION)
      .transactionStatus(UPDATED_TRANSACTION_STATUS)
      .profilCpm(UPDATED_PROFIL_CPM)
      .reaso(UPDATED_REASO)
      .signatureDate(UPDATED_SIGNATURE_DATE)
      .validationDate(UPDATED_VALIDATION_DATE);
    return raRecord;
  }

  @BeforeEach
  public void initTest() {
    raRecord = createEntity(em);
  }

  @Test
  @Transactional
  void createRaRecord() throws Exception {
    int databaseSizeBeforeCreate = raRecordRepository.findAll().size();
    // Create the RaRecord
    restRaRecordMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raRecord)))
      .andExpect(status().isCreated());

    // Validate the RaRecord in the database
    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeCreate + 1);
    RaRecord testRaRecord = raRecordList.get(raRecordList.size() - 1);
    assertThat(testRaRecord.getStatus()).isEqualTo(DEFAULT_STATUS);
    assertThat(testRaRecord.getIdUser()).isEqualTo(DEFAULT_ID_USER);
    assertThat(testRaRecord.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
    assertThat(testRaRecord.getCertO()).isEqualTo(DEFAULT_CERT_O);
    assertThat(testRaRecord.getCommonName()).isEqualTo(DEFAULT_COMMON_NAME);
    assertThat(testRaRecord.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
    assertThat(testRaRecord.getLocality()).isEqualTo(DEFAULT_LOCALITY);
    assertThat(testRaRecord.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    assertThat(testRaRecord.getLastname()).isEqualTo(DEFAULT_LASTNAME);
    assertThat(testRaRecord.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
    assertThat(testRaRecord.getEmail()).isEqualTo(DEFAULT_EMAIL);
    assertThat(testRaRecord.getPhone()).isEqualTo(DEFAULT_PHONE);
    assertThat(testRaRecord.getUrl()).isEqualTo(DEFAULT_URL);
    assertThat(testRaRecord.getIdTransaction()).isEqualTo(DEFAULT_ID_TRANSACTION);
    assertThat(testRaRecord.getTransactionStatus()).isEqualTo(DEFAULT_TRANSACTION_STATUS);
    assertThat(testRaRecord.getProfilCpm()).isEqualTo(DEFAULT_PROFIL_CPM);
    assertThat(testRaRecord.getReaso()).isEqualTo(DEFAULT_REASO);
    assertThat(testRaRecord.getSignatureDate()).isEqualTo(DEFAULT_SIGNATURE_DATE);
    assertThat(testRaRecord.getValidationDate()).isEqualTo(DEFAULT_VALIDATION_DATE);
  }

  @Test
  @Transactional
  void createRaRecordWithExistingId() throws Exception {
    // Create the RaRecord with an existing ID
    raRecord.setId(1L);

    int databaseSizeBeforeCreate = raRecordRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restRaRecordMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raRecord)))
      .andExpect(status().isBadRequest());

    // Validate the RaRecord in the database
    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkStatusIsRequired() throws Exception {
    int databaseSizeBeforeTest = raRecordRepository.findAll().size();
    // set the field null
    raRecord.setStatus(null);

    // Create the RaRecord, which fails.

    restRaRecordMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raRecord)))
      .andExpect(status().isBadRequest());

    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkIdUserIsRequired() throws Exception {
    int databaseSizeBeforeTest = raRecordRepository.findAll().size();
    // set the field null
    raRecord.setIdUser(null);

    // Create the RaRecord, which fails.

    restRaRecordMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raRecord)))
      .andExpect(status().isBadRequest());

    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkIdentifierIsRequired() throws Exception {
    int databaseSizeBeforeTest = raRecordRepository.findAll().size();
    // set the field null
    raRecord.setIdentifier(null);

    // Create the RaRecord, which fails.

    restRaRecordMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raRecord)))
      .andExpect(status().isBadRequest());

    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkCertOIsRequired() throws Exception {
    int databaseSizeBeforeTest = raRecordRepository.findAll().size();
    // set the field null
    raRecord.setCertO(null);

    // Create the RaRecord, which fails.

    restRaRecordMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raRecord)))
      .andExpect(status().isBadRequest());

    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkCommonNameIsRequired() throws Exception {
    int databaseSizeBeforeTest = raRecordRepository.findAll().size();
    // set the field null
    raRecord.setCommonName(null);

    // Create the RaRecord, which fails.

    restRaRecordMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raRecord)))
      .andExpect(status().isBadRequest());

    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkZipCodeIsRequired() throws Exception {
    int databaseSizeBeforeTest = raRecordRepository.findAll().size();
    // set the field null
    raRecord.setZipCode(null);

    // Create the RaRecord, which fails.

    restRaRecordMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raRecord)))
      .andExpect(status().isBadRequest());

    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkLocalityIsRequired() throws Exception {
    int databaseSizeBeforeTest = raRecordRepository.findAll().size();
    // set the field null
    raRecord.setLocality(null);

    // Create the RaRecord, which fails.

    restRaRecordMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raRecord)))
      .andExpect(status().isBadRequest());

    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkCountryIsRequired() throws Exception {
    int databaseSizeBeforeTest = raRecordRepository.findAll().size();
    // set the field null
    raRecord.setCountry(null);

    // Create the RaRecord, which fails.

    restRaRecordMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raRecord)))
      .andExpect(status().isBadRequest());

    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkLastnameIsRequired() throws Exception {
    int databaseSizeBeforeTest = raRecordRepository.findAll().size();
    // set the field null
    raRecord.setLastname(null);

    // Create the RaRecord, which fails.

    restRaRecordMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raRecord)))
      .andExpect(status().isBadRequest());

    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkFirstnameIsRequired() throws Exception {
    int databaseSizeBeforeTest = raRecordRepository.findAll().size();
    // set the field null
    raRecord.setFirstname(null);

    // Create the RaRecord, which fails.

    restRaRecordMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raRecord)))
      .andExpect(status().isBadRequest());

    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkEmailIsRequired() throws Exception {
    int databaseSizeBeforeTest = raRecordRepository.findAll().size();
    // set the field null
    raRecord.setEmail(null);

    // Create the RaRecord, which fails.

    restRaRecordMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raRecord)))
      .andExpect(status().isBadRequest());

    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkPhoneIsRequired() throws Exception {
    int databaseSizeBeforeTest = raRecordRepository.findAll().size();
    // set the field null
    raRecord.setPhone(null);

    // Create the RaRecord, which fails.

    restRaRecordMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raRecord)))
      .andExpect(status().isBadRequest());

    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllRaRecords() throws Exception {
    // Initialize the database
    raRecordRepository.saveAndFlush(raRecord);

    // Get all the raRecordList
    restRaRecordMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(raRecord.getId().intValue())))
      .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
      .andExpect(jsonPath("$.[*].idUser").value(hasItem(DEFAULT_ID_USER)))
      .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
      .andExpect(jsonPath("$.[*].certO").value(hasItem(DEFAULT_CERT_O)))
      .andExpect(jsonPath("$.[*].commonName").value(hasItem(DEFAULT_COMMON_NAME)))
      .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
      .andExpect(jsonPath("$.[*].locality").value(hasItem(DEFAULT_LOCALITY)))
      .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
      .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
      .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
      .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
      .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
      .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
      .andExpect(jsonPath("$.[*].idTransaction").value(hasItem(DEFAULT_ID_TRANSACTION)))
      .andExpect(jsonPath("$.[*].transactionStatus").value(hasItem(DEFAULT_TRANSACTION_STATUS)))
      .andExpect(jsonPath("$.[*].profilCpm").value(hasItem(DEFAULT_PROFIL_CPM)))
      .andExpect(jsonPath("$.[*].reaso").value(hasItem(DEFAULT_REASO)))
      .andExpect(jsonPath("$.[*].signatureDate").value(hasItem(sameInstant(DEFAULT_SIGNATURE_DATE))))
      .andExpect(jsonPath("$.[*].validationDate").value(hasItem(sameInstant(DEFAULT_VALIDATION_DATE))));
  }

  @Test
  @Transactional
  void getRaRecord() throws Exception {
    // Initialize the database
    raRecordRepository.saveAndFlush(raRecord);

    // Get the raRecord
    restRaRecordMockMvc
      .perform(get(ENTITY_API_URL_ID, raRecord.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(raRecord.getId().intValue()))
      .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
      .andExpect(jsonPath("$.idUser").value(DEFAULT_ID_USER))
      .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER))
      .andExpect(jsonPath("$.certO").value(DEFAULT_CERT_O))
      .andExpect(jsonPath("$.commonName").value(DEFAULT_COMMON_NAME))
      .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE))
      .andExpect(jsonPath("$.locality").value(DEFAULT_LOCALITY))
      .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
      .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME))
      .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME))
      .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
      .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
      .andExpect(jsonPath("$.url").value(DEFAULT_URL))
      .andExpect(jsonPath("$.idTransaction").value(DEFAULT_ID_TRANSACTION))
      .andExpect(jsonPath("$.transactionStatus").value(DEFAULT_TRANSACTION_STATUS))
      .andExpect(jsonPath("$.profilCpm").value(DEFAULT_PROFIL_CPM))
      .andExpect(jsonPath("$.reaso").value(DEFAULT_REASO))
      .andExpect(jsonPath("$.signatureDate").value(sameInstant(DEFAULT_SIGNATURE_DATE)))
      .andExpect(jsonPath("$.validationDate").value(sameInstant(DEFAULT_VALIDATION_DATE)));
  }

  @Test
  @Transactional
  void getNonExistingRaRecord() throws Exception {
    // Get the raRecord
    restRaRecordMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewRaRecord() throws Exception {
    // Initialize the database
    raRecordRepository.saveAndFlush(raRecord);

    int databaseSizeBeforeUpdate = raRecordRepository.findAll().size();

    // Update the raRecord
    RaRecord updatedRaRecord = raRecordRepository.findById(raRecord.getId()).get();
    // Disconnect from session so that the updates on updatedRaRecord are not directly saved in db
    em.detach(updatedRaRecord);
    updatedRaRecord
      .status(UPDATED_STATUS)
      .idUser(UPDATED_ID_USER)
      .identifier(UPDATED_IDENTIFIER)
      .certO(UPDATED_CERT_O)
      .commonName(UPDATED_COMMON_NAME)
      .zipCode(UPDATED_ZIP_CODE)
      .locality(UPDATED_LOCALITY)
      .country(UPDATED_COUNTRY)
      .lastname(UPDATED_LASTNAME)
      .firstname(UPDATED_FIRSTNAME)
      .email(UPDATED_EMAIL)
      .phone(UPDATED_PHONE)
      .url(UPDATED_URL)
      .idTransaction(UPDATED_ID_TRANSACTION)
      .transactionStatus(UPDATED_TRANSACTION_STATUS)
      .profilCpm(UPDATED_PROFIL_CPM)
      .reaso(UPDATED_REASO)
      .signatureDate(UPDATED_SIGNATURE_DATE)
      .validationDate(UPDATED_VALIDATION_DATE);

    restRaRecordMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedRaRecord.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedRaRecord))
      )
      .andExpect(status().isOk());

    // Validate the RaRecord in the database
    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeUpdate);
    RaRecord testRaRecord = raRecordList.get(raRecordList.size() - 1);
    assertThat(testRaRecord.getStatus()).isEqualTo(UPDATED_STATUS);
    assertThat(testRaRecord.getIdUser()).isEqualTo(UPDATED_ID_USER);
    assertThat(testRaRecord.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
    assertThat(testRaRecord.getCertO()).isEqualTo(UPDATED_CERT_O);
    assertThat(testRaRecord.getCommonName()).isEqualTo(UPDATED_COMMON_NAME);
    assertThat(testRaRecord.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
    assertThat(testRaRecord.getLocality()).isEqualTo(UPDATED_LOCALITY);
    assertThat(testRaRecord.getCountry()).isEqualTo(UPDATED_COUNTRY);
    assertThat(testRaRecord.getLastname()).isEqualTo(UPDATED_LASTNAME);
    assertThat(testRaRecord.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
    assertThat(testRaRecord.getEmail()).isEqualTo(UPDATED_EMAIL);
    assertThat(testRaRecord.getPhone()).isEqualTo(UPDATED_PHONE);
    assertThat(testRaRecord.getUrl()).isEqualTo(UPDATED_URL);
    assertThat(testRaRecord.getIdTransaction()).isEqualTo(UPDATED_ID_TRANSACTION);
    assertThat(testRaRecord.getTransactionStatus()).isEqualTo(UPDATED_TRANSACTION_STATUS);
    assertThat(testRaRecord.getProfilCpm()).isEqualTo(UPDATED_PROFIL_CPM);
    assertThat(testRaRecord.getReaso()).isEqualTo(UPDATED_REASO);
    assertThat(testRaRecord.getSignatureDate()).isEqualTo(UPDATED_SIGNATURE_DATE);
    assertThat(testRaRecord.getValidationDate()).isEqualTo(UPDATED_VALIDATION_DATE);
  }

  @Test
  @Transactional
  void putNonExistingRaRecord() throws Exception {
    int databaseSizeBeforeUpdate = raRecordRepository.findAll().size();
    raRecord.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restRaRecordMockMvc
      .perform(
        put(ENTITY_API_URL_ID, raRecord.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(raRecord))
      )
      .andExpect(status().isBadRequest());

    // Validate the RaRecord in the database
    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchRaRecord() throws Exception {
    int databaseSizeBeforeUpdate = raRecordRepository.findAll().size();
    raRecord.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restRaRecordMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(raRecord))
      )
      .andExpect(status().isBadRequest());

    // Validate the RaRecord in the database
    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamRaRecord() throws Exception {
    int databaseSizeBeforeUpdate = raRecordRepository.findAll().size();
    raRecord.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restRaRecordMockMvc
      .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raRecord)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the RaRecord in the database
    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateRaRecordWithPatch() throws Exception {
    // Initialize the database
    raRecordRepository.saveAndFlush(raRecord);

    int databaseSizeBeforeUpdate = raRecordRepository.findAll().size();

    // Update the raRecord using partial update
    RaRecord partialUpdatedRaRecord = new RaRecord();
    partialUpdatedRaRecord.setId(raRecord.getId());

    partialUpdatedRaRecord
      .status(UPDATED_STATUS)
      .certO(UPDATED_CERT_O)
      .commonName(UPDATED_COMMON_NAME)
      .zipCode(UPDATED_ZIP_CODE)
      .idTransaction(UPDATED_ID_TRANSACTION)
      .transactionStatus(UPDATED_TRANSACTION_STATUS)
      .signatureDate(UPDATED_SIGNATURE_DATE);

    restRaRecordMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedRaRecord.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRaRecord))
      )
      .andExpect(status().isOk());

    // Validate the RaRecord in the database
    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeUpdate);
    RaRecord testRaRecord = raRecordList.get(raRecordList.size() - 1);
    assertThat(testRaRecord.getStatus()).isEqualTo(UPDATED_STATUS);
    assertThat(testRaRecord.getIdUser()).isEqualTo(DEFAULT_ID_USER);
    assertThat(testRaRecord.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
    assertThat(testRaRecord.getCertO()).isEqualTo(UPDATED_CERT_O);
    assertThat(testRaRecord.getCommonName()).isEqualTo(UPDATED_COMMON_NAME);
    assertThat(testRaRecord.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
    assertThat(testRaRecord.getLocality()).isEqualTo(DEFAULT_LOCALITY);
    assertThat(testRaRecord.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    assertThat(testRaRecord.getLastname()).isEqualTo(DEFAULT_LASTNAME);
    assertThat(testRaRecord.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
    assertThat(testRaRecord.getEmail()).isEqualTo(DEFAULT_EMAIL);
    assertThat(testRaRecord.getPhone()).isEqualTo(DEFAULT_PHONE);
    assertThat(testRaRecord.getUrl()).isEqualTo(DEFAULT_URL);
    assertThat(testRaRecord.getIdTransaction()).isEqualTo(UPDATED_ID_TRANSACTION);
    assertThat(testRaRecord.getTransactionStatus()).isEqualTo(UPDATED_TRANSACTION_STATUS);
    assertThat(testRaRecord.getProfilCpm()).isEqualTo(DEFAULT_PROFIL_CPM);
    assertThat(testRaRecord.getReaso()).isEqualTo(DEFAULT_REASO);
    assertThat(testRaRecord.getSignatureDate()).isEqualTo(UPDATED_SIGNATURE_DATE);
    assertThat(testRaRecord.getValidationDate()).isEqualTo(DEFAULT_VALIDATION_DATE);
  }

  @Test
  @Transactional
  void fullUpdateRaRecordWithPatch() throws Exception {
    // Initialize the database
    raRecordRepository.saveAndFlush(raRecord);

    int databaseSizeBeforeUpdate = raRecordRepository.findAll().size();

    // Update the raRecord using partial update
    RaRecord partialUpdatedRaRecord = new RaRecord();
    partialUpdatedRaRecord.setId(raRecord.getId());

    partialUpdatedRaRecord
      .status(UPDATED_STATUS)
      .idUser(UPDATED_ID_USER)
      .identifier(UPDATED_IDENTIFIER)
      .certO(UPDATED_CERT_O)
      .commonName(UPDATED_COMMON_NAME)
      .zipCode(UPDATED_ZIP_CODE)
      .locality(UPDATED_LOCALITY)
      .country(UPDATED_COUNTRY)
      .lastname(UPDATED_LASTNAME)
      .firstname(UPDATED_FIRSTNAME)
      .email(UPDATED_EMAIL)
      .phone(UPDATED_PHONE)
      .url(UPDATED_URL)
      .idTransaction(UPDATED_ID_TRANSACTION)
      .transactionStatus(UPDATED_TRANSACTION_STATUS)
      .profilCpm(UPDATED_PROFIL_CPM)
      .reaso(UPDATED_REASO)
      .signatureDate(UPDATED_SIGNATURE_DATE)
      .validationDate(UPDATED_VALIDATION_DATE);

    restRaRecordMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedRaRecord.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRaRecord))
      )
      .andExpect(status().isOk());

    // Validate the RaRecord in the database
    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeUpdate);
    RaRecord testRaRecord = raRecordList.get(raRecordList.size() - 1);
    assertThat(testRaRecord.getStatus()).isEqualTo(UPDATED_STATUS);
    assertThat(testRaRecord.getIdUser()).isEqualTo(UPDATED_ID_USER);
    assertThat(testRaRecord.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
    assertThat(testRaRecord.getCertO()).isEqualTo(UPDATED_CERT_O);
    assertThat(testRaRecord.getCommonName()).isEqualTo(UPDATED_COMMON_NAME);
    assertThat(testRaRecord.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
    assertThat(testRaRecord.getLocality()).isEqualTo(UPDATED_LOCALITY);
    assertThat(testRaRecord.getCountry()).isEqualTo(UPDATED_COUNTRY);
    assertThat(testRaRecord.getLastname()).isEqualTo(UPDATED_LASTNAME);
    assertThat(testRaRecord.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
    assertThat(testRaRecord.getEmail()).isEqualTo(UPDATED_EMAIL);
    assertThat(testRaRecord.getPhone()).isEqualTo(UPDATED_PHONE);
    assertThat(testRaRecord.getUrl()).isEqualTo(UPDATED_URL);
    assertThat(testRaRecord.getIdTransaction()).isEqualTo(UPDATED_ID_TRANSACTION);
    assertThat(testRaRecord.getTransactionStatus()).isEqualTo(UPDATED_TRANSACTION_STATUS);
    assertThat(testRaRecord.getProfilCpm()).isEqualTo(UPDATED_PROFIL_CPM);
    assertThat(testRaRecord.getReaso()).isEqualTo(UPDATED_REASO);
    assertThat(testRaRecord.getSignatureDate()).isEqualTo(UPDATED_SIGNATURE_DATE);
    assertThat(testRaRecord.getValidationDate()).isEqualTo(UPDATED_VALIDATION_DATE);
  }

  @Test
  @Transactional
  void patchNonExistingRaRecord() throws Exception {
    int databaseSizeBeforeUpdate = raRecordRepository.findAll().size();
    raRecord.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restRaRecordMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, raRecord.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(raRecord))
      )
      .andExpect(status().isBadRequest());

    // Validate the RaRecord in the database
    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchRaRecord() throws Exception {
    int databaseSizeBeforeUpdate = raRecordRepository.findAll().size();
    raRecord.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restRaRecordMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(raRecord))
      )
      .andExpect(status().isBadRequest());

    // Validate the RaRecord in the database
    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamRaRecord() throws Exception {
    int databaseSizeBeforeUpdate = raRecordRepository.findAll().size();
    raRecord.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restRaRecordMockMvc
      .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(raRecord)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the RaRecord in the database
    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteRaRecord() throws Exception {
    // Initialize the database
    raRecordRepository.saveAndFlush(raRecord);

    int databaseSizeBeforeDelete = raRecordRepository.findAll().size();

    // Delete the raRecord
    restRaRecordMockMvc
      .perform(delete(ENTITY_API_URL_ID, raRecord.getId()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<RaRecord> raRecordList = raRecordRepository.findAll();
    assertThat(raRecordList).hasSize(databaseSizeBeforeDelete - 1);
  }
}
