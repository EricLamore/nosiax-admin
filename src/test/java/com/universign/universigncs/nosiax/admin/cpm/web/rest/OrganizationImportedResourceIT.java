package com.universign.universigncs.nosiax.admin.cpm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.nosiax.admin.cpm.IntegrationTest;
import com.universign.universigncs.nosiax.admin.cpm.domain.OrganizationImported;
import com.universign.universigncs.nosiax.admin.cpm.domain.enumeration.Language;
import com.universign.universigncs.nosiax.admin.cpm.repository.OrganizationImportedRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link OrganizationImportedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganizationImportedResourceIT {

  private static final String DEFAULT_CLIENT = "AAAAAAAAAA";
  private static final String UPDATED_CLIENT = "BBBBBBBBBB";

  private static final String DEFAULT_DISPLAYNAME = "AAAAAAAAAA";
  private static final String UPDATED_DISPLAYNAME = "BBBBBBBBBB";

  private static final String DEFAULT_LOGO = "AAAAAAAAAA";
  private static final String UPDATED_LOGO = "BBBBBBBBBB";

  private static final String DEFAULT_PROFILE_NAME = "AAAAAAAAAA";
  private static final String UPDATED_PROFILE_NAME = "BBBBBBBBBB";

  private static final Boolean DEFAULT_CONSO_SIG = false;
  private static final Boolean UPDATED_CONSO_SIG = true;

  private static final Boolean DEFAULT_CONSO_TIMES = false;
  private static final Boolean UPDATED_CONSO_TIMES = true;

  private static final Boolean DEFAULT_CONSO_SEAL = false;
  private static final Boolean UPDATED_CONSO_SEAL = true;

  private static final Boolean DEFAULT_TECHNICAL_ACCOUNT_CREATION = false;
  private static final Boolean UPDATED_TECHNICAL_ACCOUNT_CREATION = true;

  private static final Boolean DEFAULT_IS_TECHNICAL_ACCOUNT_ADMIN = false;
  private static final Boolean UPDATED_IS_TECHNICAL_ACCOUNT_ADMIN = true;

  private static final Boolean DEFAULT_IS_TECHNICAL_ACCOUNT = false;
  private static final Boolean UPDATED_IS_TECHNICAL_ACCOUNT = true;

  private static final Boolean DEFAULT_IS_OPERATOR = false;
  private static final Boolean UPDATED_IS_OPERATOR = true;

  private static final String DEFAULT_TECHNICAL_ACCOUNT_FIRSTNAME = "AAAAAAAAAA";
  private static final String UPDATED_TECHNICAL_ACCOUNT_FIRSTNAME = "BBBBBBBBBB";

  private static final String DEFAULT_TECHNICAL_ACCOUNT_LASTNAME = "AAAAAAAAAA";
  private static final String UPDATED_TECHNICAL_ACCOUNT_LASTNAME = "BBBBBBBBBB";

  private static final String DEFAULT_TECHNICAL_ACCOUNT_MAIL = "AAAAAAAAAA";
  private static final String UPDATED_TECHNICAL_ACCOUNT_MAIL = "BBBBBBBBBB";

  private static final Language DEFAULT_LANGUAGE = Language.FRENCH;
  private static final Language UPDATED_LANGUAGE = Language.ENGLISH;

  private static final Boolean DEFAULT_ORG_CREATED = false;
  private static final Boolean UPDATED_ORG_CREATED = true;

  private static final Boolean DEFAULT_TECHNICAL_ACCOUNT_CREATED = false;
  private static final Boolean UPDATED_TECHNICAL_ACCOUNT_CREATED = true;

  private static final Boolean DEFAULT_CONSO_CREATED = false;
  private static final Boolean UPDATED_CONSO_CREATED = true;

  private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

  private static final String ENTITY_API_URL = "/api/organization-importeds";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private OrganizationImportedRepository organizationImportedRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restOrganizationImportedMockMvc;

  private OrganizationImported organizationImported;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static OrganizationImported createEntity(EntityManager em) {
    OrganizationImported organizationImported = new OrganizationImported()
      .client(DEFAULT_CLIENT)
      .displayname(DEFAULT_DISPLAYNAME)
      .logo(DEFAULT_LOGO)
      .profileName(DEFAULT_PROFILE_NAME)
      .consoSig(DEFAULT_CONSO_SIG)
      .consoTimes(DEFAULT_CONSO_TIMES)
      .consoSeal(DEFAULT_CONSO_SEAL)
      .technicalAccountCreation(DEFAULT_TECHNICAL_ACCOUNT_CREATION)
      .isTechnicalAccountAdmin(DEFAULT_IS_TECHNICAL_ACCOUNT_ADMIN)
      .isTechnicalAccount(DEFAULT_IS_TECHNICAL_ACCOUNT)
      .isOperator(DEFAULT_IS_OPERATOR)
      .technicalAccountFirstname(DEFAULT_TECHNICAL_ACCOUNT_FIRSTNAME)
      .technicalAccountLastname(DEFAULT_TECHNICAL_ACCOUNT_LASTNAME)
      .technicalAccountMail(DEFAULT_TECHNICAL_ACCOUNT_MAIL)
      .language(DEFAULT_LANGUAGE)
      .orgCreated(DEFAULT_ORG_CREATED)
      .technicalAccountCreated(DEFAULT_TECHNICAL_ACCOUNT_CREATED)
      .consoCreated(DEFAULT_CONSO_CREATED)
      .createDate(DEFAULT_CREATE_DATE);
    return organizationImported;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static OrganizationImported createUpdatedEntity(EntityManager em) {
    OrganizationImported organizationImported = new OrganizationImported()
      .client(UPDATED_CLIENT)
      .displayname(UPDATED_DISPLAYNAME)
      .logo(UPDATED_LOGO)
      .profileName(UPDATED_PROFILE_NAME)
      .consoSig(UPDATED_CONSO_SIG)
      .consoTimes(UPDATED_CONSO_TIMES)
      .consoSeal(UPDATED_CONSO_SEAL)
      .technicalAccountCreation(UPDATED_TECHNICAL_ACCOUNT_CREATION)
      .isTechnicalAccountAdmin(UPDATED_IS_TECHNICAL_ACCOUNT_ADMIN)
      .isTechnicalAccount(UPDATED_IS_TECHNICAL_ACCOUNT)
      .isOperator(UPDATED_IS_OPERATOR)
      .technicalAccountFirstname(UPDATED_TECHNICAL_ACCOUNT_FIRSTNAME)
      .technicalAccountLastname(UPDATED_TECHNICAL_ACCOUNT_LASTNAME)
      .technicalAccountMail(UPDATED_TECHNICAL_ACCOUNT_MAIL)
      .language(UPDATED_LANGUAGE)
      .orgCreated(UPDATED_ORG_CREATED)
      .technicalAccountCreated(UPDATED_TECHNICAL_ACCOUNT_CREATED)
      .consoCreated(UPDATED_CONSO_CREATED)
      .createDate(UPDATED_CREATE_DATE);
    return organizationImported;
  }

  @BeforeEach
  public void initTest() {
    organizationImported = createEntity(em);
  }

  @Test
  @Transactional
  void createOrganizationImported() throws Exception {
    int databaseSizeBeforeCreate = organizationImportedRepository.findAll().size();
    // Create the OrganizationImported
    restOrganizationImportedMockMvc
      .perform(
        post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organizationImported))
      )
      .andExpect(status().isCreated());

    // Validate the OrganizationImported in the database
    List<OrganizationImported> organizationImportedList = organizationImportedRepository.findAll();
    assertThat(organizationImportedList).hasSize(databaseSizeBeforeCreate + 1);
    OrganizationImported testOrganizationImported = organizationImportedList.get(organizationImportedList.size() - 1);
    assertThat(testOrganizationImported.getClient()).isEqualTo(DEFAULT_CLIENT);
    assertThat(testOrganizationImported.getDisplayname()).isEqualTo(DEFAULT_DISPLAYNAME);
    assertThat(testOrganizationImported.getLogo()).isEqualTo(DEFAULT_LOGO);
    assertThat(testOrganizationImported.getProfileName()).isEqualTo(DEFAULT_PROFILE_NAME);
    assertThat(testOrganizationImported.getConsoSig()).isEqualTo(DEFAULT_CONSO_SIG);
    assertThat(testOrganizationImported.getConsoTimes()).isEqualTo(DEFAULT_CONSO_TIMES);
    assertThat(testOrganizationImported.getConsoSeal()).isEqualTo(DEFAULT_CONSO_SEAL);
    assertThat(testOrganizationImported.getTechnicalAccountCreation()).isEqualTo(DEFAULT_TECHNICAL_ACCOUNT_CREATION);
    assertThat(testOrganizationImported.getIsTechnicalAccountAdmin()).isEqualTo(DEFAULT_IS_TECHNICAL_ACCOUNT_ADMIN);
    assertThat(testOrganizationImported.getIsTechnicalAccount()).isEqualTo(DEFAULT_IS_TECHNICAL_ACCOUNT);
    assertThat(testOrganizationImported.getIsOperator()).isEqualTo(DEFAULT_IS_OPERATOR);
    assertThat(testOrganizationImported.getTechnicalAccountFirstname()).isEqualTo(DEFAULT_TECHNICAL_ACCOUNT_FIRSTNAME);
    assertThat(testOrganizationImported.getTechnicalAccountLastname()).isEqualTo(DEFAULT_TECHNICAL_ACCOUNT_LASTNAME);
    assertThat(testOrganizationImported.getTechnicalAccountMail()).isEqualTo(DEFAULT_TECHNICAL_ACCOUNT_MAIL);
    assertThat(testOrganizationImported.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    assertThat(testOrganizationImported.getOrgCreated()).isEqualTo(DEFAULT_ORG_CREATED);
    assertThat(testOrganizationImported.getTechnicalAccountCreated()).isEqualTo(DEFAULT_TECHNICAL_ACCOUNT_CREATED);
    assertThat(testOrganizationImported.getConsoCreated()).isEqualTo(DEFAULT_CONSO_CREATED);
    assertThat(testOrganizationImported.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
  }

  @Test
  @Transactional
  void createOrganizationImportedWithExistingId() throws Exception {
    // Create the OrganizationImported with an existing ID
    organizationImported.setId(1L);

    int databaseSizeBeforeCreate = organizationImportedRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restOrganizationImportedMockMvc
      .perform(
        post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organizationImported))
      )
      .andExpect(status().isBadRequest());

    // Validate the OrganizationImported in the database
    List<OrganizationImported> organizationImportedList = organizationImportedRepository.findAll();
    assertThat(organizationImportedList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void getAllOrganizationImporteds() throws Exception {
    // Initialize the database
    organizationImportedRepository.saveAndFlush(organizationImported);

    // Get all the organizationImportedList
    restOrganizationImportedMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(organizationImported.getId().intValue())))
      .andExpect(jsonPath("$.[*].client").value(hasItem(DEFAULT_CLIENT)))
      .andExpect(jsonPath("$.[*].displayname").value(hasItem(DEFAULT_DISPLAYNAME)))
      .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO)))
      .andExpect(jsonPath("$.[*].profileName").value(hasItem(DEFAULT_PROFILE_NAME)))
      .andExpect(jsonPath("$.[*].consoSig").value(hasItem(DEFAULT_CONSO_SIG.booleanValue())))
      .andExpect(jsonPath("$.[*].consoTimes").value(hasItem(DEFAULT_CONSO_TIMES.booleanValue())))
      .andExpect(jsonPath("$.[*].consoSeal").value(hasItem(DEFAULT_CONSO_SEAL.booleanValue())))
      .andExpect(jsonPath("$.[*].technicalAccountCreation").value(hasItem(DEFAULT_TECHNICAL_ACCOUNT_CREATION.booleanValue())))
      .andExpect(jsonPath("$.[*].isTechnicalAccountAdmin").value(hasItem(DEFAULT_IS_TECHNICAL_ACCOUNT_ADMIN.booleanValue())))
      .andExpect(jsonPath("$.[*].isTechnicalAccount").value(hasItem(DEFAULT_IS_TECHNICAL_ACCOUNT.booleanValue())))
      .andExpect(jsonPath("$.[*].isOperator").value(hasItem(DEFAULT_IS_OPERATOR.booleanValue())))
      .andExpect(jsonPath("$.[*].technicalAccountFirstname").value(hasItem(DEFAULT_TECHNICAL_ACCOUNT_FIRSTNAME)))
      .andExpect(jsonPath("$.[*].technicalAccountLastname").value(hasItem(DEFAULT_TECHNICAL_ACCOUNT_LASTNAME)))
      .andExpect(jsonPath("$.[*].technicalAccountMail").value(hasItem(DEFAULT_TECHNICAL_ACCOUNT_MAIL)))
      .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
      .andExpect(jsonPath("$.[*].orgCreated").value(hasItem(DEFAULT_ORG_CREATED.booleanValue())))
      .andExpect(jsonPath("$.[*].technicalAccountCreated").value(hasItem(DEFAULT_TECHNICAL_ACCOUNT_CREATED.booleanValue())))
      .andExpect(jsonPath("$.[*].consoCreated").value(hasItem(DEFAULT_CONSO_CREATED.booleanValue())))
      .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())));
  }

  @Test
  @Transactional
  void getOrganizationImported() throws Exception {
    // Initialize the database
    organizationImportedRepository.saveAndFlush(organizationImported);

    // Get the organizationImported
    restOrganizationImportedMockMvc
      .perform(get(ENTITY_API_URL_ID, organizationImported.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(organizationImported.getId().intValue()))
      .andExpect(jsonPath("$.client").value(DEFAULT_CLIENT))
      .andExpect(jsonPath("$.displayname").value(DEFAULT_DISPLAYNAME))
      .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO))
      .andExpect(jsonPath("$.profileName").value(DEFAULT_PROFILE_NAME))
      .andExpect(jsonPath("$.consoSig").value(DEFAULT_CONSO_SIG.booleanValue()))
      .andExpect(jsonPath("$.consoTimes").value(DEFAULT_CONSO_TIMES.booleanValue()))
      .andExpect(jsonPath("$.consoSeal").value(DEFAULT_CONSO_SEAL.booleanValue()))
      .andExpect(jsonPath("$.technicalAccountCreation").value(DEFAULT_TECHNICAL_ACCOUNT_CREATION.booleanValue()))
      .andExpect(jsonPath("$.isTechnicalAccountAdmin").value(DEFAULT_IS_TECHNICAL_ACCOUNT_ADMIN.booleanValue()))
      .andExpect(jsonPath("$.isTechnicalAccount").value(DEFAULT_IS_TECHNICAL_ACCOUNT.booleanValue()))
      .andExpect(jsonPath("$.isOperator").value(DEFAULT_IS_OPERATOR.booleanValue()))
      .andExpect(jsonPath("$.technicalAccountFirstname").value(DEFAULT_TECHNICAL_ACCOUNT_FIRSTNAME))
      .andExpect(jsonPath("$.technicalAccountLastname").value(DEFAULT_TECHNICAL_ACCOUNT_LASTNAME))
      .andExpect(jsonPath("$.technicalAccountMail").value(DEFAULT_TECHNICAL_ACCOUNT_MAIL))
      .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
      .andExpect(jsonPath("$.orgCreated").value(DEFAULT_ORG_CREATED.booleanValue()))
      .andExpect(jsonPath("$.technicalAccountCreated").value(DEFAULT_TECHNICAL_ACCOUNT_CREATED.booleanValue()))
      .andExpect(jsonPath("$.consoCreated").value(DEFAULT_CONSO_CREATED.booleanValue()))
      .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()));
  }

  @Test
  @Transactional
  void getNonExistingOrganizationImported() throws Exception {
    // Get the organizationImported
    restOrganizationImportedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewOrganizationImported() throws Exception {
    // Initialize the database
    organizationImportedRepository.saveAndFlush(organizationImported);

    int databaseSizeBeforeUpdate = organizationImportedRepository.findAll().size();

    // Update the organizationImported
    OrganizationImported updatedOrganizationImported = organizationImportedRepository.findById(organizationImported.getId()).get();
    // Disconnect from session so that the updates on updatedOrganizationImported are not directly saved in db
    em.detach(updatedOrganizationImported);
    updatedOrganizationImported
      .client(UPDATED_CLIENT)
      .displayname(UPDATED_DISPLAYNAME)
      .logo(UPDATED_LOGO)
      .profileName(UPDATED_PROFILE_NAME)
      .consoSig(UPDATED_CONSO_SIG)
      .consoTimes(UPDATED_CONSO_TIMES)
      .consoSeal(UPDATED_CONSO_SEAL)
      .technicalAccountCreation(UPDATED_TECHNICAL_ACCOUNT_CREATION)
      .isTechnicalAccountAdmin(UPDATED_IS_TECHNICAL_ACCOUNT_ADMIN)
      .isTechnicalAccount(UPDATED_IS_TECHNICAL_ACCOUNT)
      .isOperator(UPDATED_IS_OPERATOR)
      .technicalAccountFirstname(UPDATED_TECHNICAL_ACCOUNT_FIRSTNAME)
      .technicalAccountLastname(UPDATED_TECHNICAL_ACCOUNT_LASTNAME)
      .technicalAccountMail(UPDATED_TECHNICAL_ACCOUNT_MAIL)
      .language(UPDATED_LANGUAGE)
      .orgCreated(UPDATED_ORG_CREATED)
      .technicalAccountCreated(UPDATED_TECHNICAL_ACCOUNT_CREATED)
      .consoCreated(UPDATED_CONSO_CREATED)
      .createDate(UPDATED_CREATE_DATE);

    restOrganizationImportedMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedOrganizationImported.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedOrganizationImported))
      )
      .andExpect(status().isOk());

    // Validate the OrganizationImported in the database
    List<OrganizationImported> organizationImportedList = organizationImportedRepository.findAll();
    assertThat(organizationImportedList).hasSize(databaseSizeBeforeUpdate);
    OrganizationImported testOrganizationImported = organizationImportedList.get(organizationImportedList.size() - 1);
    assertThat(testOrganizationImported.getClient()).isEqualTo(UPDATED_CLIENT);
    assertThat(testOrganizationImported.getDisplayname()).isEqualTo(UPDATED_DISPLAYNAME);
    assertThat(testOrganizationImported.getLogo()).isEqualTo(UPDATED_LOGO);
    assertThat(testOrganizationImported.getProfileName()).isEqualTo(UPDATED_PROFILE_NAME);
    assertThat(testOrganizationImported.getConsoSig()).isEqualTo(UPDATED_CONSO_SIG);
    assertThat(testOrganizationImported.getConsoTimes()).isEqualTo(UPDATED_CONSO_TIMES);
    assertThat(testOrganizationImported.getConsoSeal()).isEqualTo(UPDATED_CONSO_SEAL);
    assertThat(testOrganizationImported.getTechnicalAccountCreation()).isEqualTo(UPDATED_TECHNICAL_ACCOUNT_CREATION);
    assertThat(testOrganizationImported.getIsTechnicalAccountAdmin()).isEqualTo(UPDATED_IS_TECHNICAL_ACCOUNT_ADMIN);
    assertThat(testOrganizationImported.getIsTechnicalAccount()).isEqualTo(UPDATED_IS_TECHNICAL_ACCOUNT);
    assertThat(testOrganizationImported.getIsOperator()).isEqualTo(UPDATED_IS_OPERATOR);
    assertThat(testOrganizationImported.getTechnicalAccountFirstname()).isEqualTo(UPDATED_TECHNICAL_ACCOUNT_FIRSTNAME);
    assertThat(testOrganizationImported.getTechnicalAccountLastname()).isEqualTo(UPDATED_TECHNICAL_ACCOUNT_LASTNAME);
    assertThat(testOrganizationImported.getTechnicalAccountMail()).isEqualTo(UPDATED_TECHNICAL_ACCOUNT_MAIL);
    assertThat(testOrganizationImported.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    assertThat(testOrganizationImported.getOrgCreated()).isEqualTo(UPDATED_ORG_CREATED);
    assertThat(testOrganizationImported.getTechnicalAccountCreated()).isEqualTo(UPDATED_TECHNICAL_ACCOUNT_CREATED);
    assertThat(testOrganizationImported.getConsoCreated()).isEqualTo(UPDATED_CONSO_CREATED);
    assertThat(testOrganizationImported.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
  }

  @Test
  @Transactional
  void putNonExistingOrganizationImported() throws Exception {
    int databaseSizeBeforeUpdate = organizationImportedRepository.findAll().size();
    organizationImported.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restOrganizationImportedMockMvc
      .perform(
        put(ENTITY_API_URL_ID, organizationImported.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(organizationImported))
      )
      .andExpect(status().isBadRequest());

    // Validate the OrganizationImported in the database
    List<OrganizationImported> organizationImportedList = organizationImportedRepository.findAll();
    assertThat(organizationImportedList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchOrganizationImported() throws Exception {
    int databaseSizeBeforeUpdate = organizationImportedRepository.findAll().size();
    organizationImported.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restOrganizationImportedMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(organizationImported))
      )
      .andExpect(status().isBadRequest());

    // Validate the OrganizationImported in the database
    List<OrganizationImported> organizationImportedList = organizationImportedRepository.findAll();
    assertThat(organizationImportedList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamOrganizationImported() throws Exception {
    int databaseSizeBeforeUpdate = organizationImportedRepository.findAll().size();
    organizationImported.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restOrganizationImportedMockMvc
      .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organizationImported)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the OrganizationImported in the database
    List<OrganizationImported> organizationImportedList = organizationImportedRepository.findAll();
    assertThat(organizationImportedList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateOrganizationImportedWithPatch() throws Exception {
    // Initialize the database
    organizationImportedRepository.saveAndFlush(organizationImported);

    int databaseSizeBeforeUpdate = organizationImportedRepository.findAll().size();

    // Update the organizationImported using partial update
    OrganizationImported partialUpdatedOrganizationImported = new OrganizationImported();
    partialUpdatedOrganizationImported.setId(organizationImported.getId());

    partialUpdatedOrganizationImported
      .client(UPDATED_CLIENT)
      .consoSig(UPDATED_CONSO_SIG)
      .consoTimes(UPDATED_CONSO_TIMES)
      .isTechnicalAccount(UPDATED_IS_TECHNICAL_ACCOUNT)
      .isOperator(UPDATED_IS_OPERATOR)
      .technicalAccountFirstname(UPDATED_TECHNICAL_ACCOUNT_FIRSTNAME)
      .technicalAccountLastname(UPDATED_TECHNICAL_ACCOUNT_LASTNAME)
      .language(UPDATED_LANGUAGE)
      .technicalAccountCreated(UPDATED_TECHNICAL_ACCOUNT_CREATED);

    restOrganizationImportedMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedOrganizationImported.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganizationImported))
      )
      .andExpect(status().isOk());

    // Validate the OrganizationImported in the database
    List<OrganizationImported> organizationImportedList = organizationImportedRepository.findAll();
    assertThat(organizationImportedList).hasSize(databaseSizeBeforeUpdate);
    OrganizationImported testOrganizationImported = organizationImportedList.get(organizationImportedList.size() - 1);
    assertThat(testOrganizationImported.getClient()).isEqualTo(UPDATED_CLIENT);
    assertThat(testOrganizationImported.getDisplayname()).isEqualTo(DEFAULT_DISPLAYNAME);
    assertThat(testOrganizationImported.getLogo()).isEqualTo(DEFAULT_LOGO);
    assertThat(testOrganizationImported.getProfileName()).isEqualTo(DEFAULT_PROFILE_NAME);
    assertThat(testOrganizationImported.getConsoSig()).isEqualTo(UPDATED_CONSO_SIG);
    assertThat(testOrganizationImported.getConsoTimes()).isEqualTo(UPDATED_CONSO_TIMES);
    assertThat(testOrganizationImported.getConsoSeal()).isEqualTo(DEFAULT_CONSO_SEAL);
    assertThat(testOrganizationImported.getTechnicalAccountCreation()).isEqualTo(DEFAULT_TECHNICAL_ACCOUNT_CREATION);
    assertThat(testOrganizationImported.getIsTechnicalAccountAdmin()).isEqualTo(DEFAULT_IS_TECHNICAL_ACCOUNT_ADMIN);
    assertThat(testOrganizationImported.getIsTechnicalAccount()).isEqualTo(UPDATED_IS_TECHNICAL_ACCOUNT);
    assertThat(testOrganizationImported.getIsOperator()).isEqualTo(UPDATED_IS_OPERATOR);
    assertThat(testOrganizationImported.getTechnicalAccountFirstname()).isEqualTo(UPDATED_TECHNICAL_ACCOUNT_FIRSTNAME);
    assertThat(testOrganizationImported.getTechnicalAccountLastname()).isEqualTo(UPDATED_TECHNICAL_ACCOUNT_LASTNAME);
    assertThat(testOrganizationImported.getTechnicalAccountMail()).isEqualTo(DEFAULT_TECHNICAL_ACCOUNT_MAIL);
    assertThat(testOrganizationImported.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    assertThat(testOrganizationImported.getOrgCreated()).isEqualTo(DEFAULT_ORG_CREATED);
    assertThat(testOrganizationImported.getTechnicalAccountCreated()).isEqualTo(UPDATED_TECHNICAL_ACCOUNT_CREATED);
    assertThat(testOrganizationImported.getConsoCreated()).isEqualTo(DEFAULT_CONSO_CREATED);
    assertThat(testOrganizationImported.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
  }

  @Test
  @Transactional
  void fullUpdateOrganizationImportedWithPatch() throws Exception {
    // Initialize the database
    organizationImportedRepository.saveAndFlush(organizationImported);

    int databaseSizeBeforeUpdate = organizationImportedRepository.findAll().size();

    // Update the organizationImported using partial update
    OrganizationImported partialUpdatedOrganizationImported = new OrganizationImported();
    partialUpdatedOrganizationImported.setId(organizationImported.getId());

    partialUpdatedOrganizationImported
      .client(UPDATED_CLIENT)
      .displayname(UPDATED_DISPLAYNAME)
      .logo(UPDATED_LOGO)
      .profileName(UPDATED_PROFILE_NAME)
      .consoSig(UPDATED_CONSO_SIG)
      .consoTimes(UPDATED_CONSO_TIMES)
      .consoSeal(UPDATED_CONSO_SEAL)
      .technicalAccountCreation(UPDATED_TECHNICAL_ACCOUNT_CREATION)
      .isTechnicalAccountAdmin(UPDATED_IS_TECHNICAL_ACCOUNT_ADMIN)
      .isTechnicalAccount(UPDATED_IS_TECHNICAL_ACCOUNT)
      .isOperator(UPDATED_IS_OPERATOR)
      .technicalAccountFirstname(UPDATED_TECHNICAL_ACCOUNT_FIRSTNAME)
      .technicalAccountLastname(UPDATED_TECHNICAL_ACCOUNT_LASTNAME)
      .technicalAccountMail(UPDATED_TECHNICAL_ACCOUNT_MAIL)
      .language(UPDATED_LANGUAGE)
      .orgCreated(UPDATED_ORG_CREATED)
      .technicalAccountCreated(UPDATED_TECHNICAL_ACCOUNT_CREATED)
      .consoCreated(UPDATED_CONSO_CREATED)
      .createDate(UPDATED_CREATE_DATE);

    restOrganizationImportedMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedOrganizationImported.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganizationImported))
      )
      .andExpect(status().isOk());

    // Validate the OrganizationImported in the database
    List<OrganizationImported> organizationImportedList = organizationImportedRepository.findAll();
    assertThat(organizationImportedList).hasSize(databaseSizeBeforeUpdate);
    OrganizationImported testOrganizationImported = organizationImportedList.get(organizationImportedList.size() - 1);
    assertThat(testOrganizationImported.getClient()).isEqualTo(UPDATED_CLIENT);
    assertThat(testOrganizationImported.getDisplayname()).isEqualTo(UPDATED_DISPLAYNAME);
    assertThat(testOrganizationImported.getLogo()).isEqualTo(UPDATED_LOGO);
    assertThat(testOrganizationImported.getProfileName()).isEqualTo(UPDATED_PROFILE_NAME);
    assertThat(testOrganizationImported.getConsoSig()).isEqualTo(UPDATED_CONSO_SIG);
    assertThat(testOrganizationImported.getConsoTimes()).isEqualTo(UPDATED_CONSO_TIMES);
    assertThat(testOrganizationImported.getConsoSeal()).isEqualTo(UPDATED_CONSO_SEAL);
    assertThat(testOrganizationImported.getTechnicalAccountCreation()).isEqualTo(UPDATED_TECHNICAL_ACCOUNT_CREATION);
    assertThat(testOrganizationImported.getIsTechnicalAccountAdmin()).isEqualTo(UPDATED_IS_TECHNICAL_ACCOUNT_ADMIN);
    assertThat(testOrganizationImported.getIsTechnicalAccount()).isEqualTo(UPDATED_IS_TECHNICAL_ACCOUNT);
    assertThat(testOrganizationImported.getIsOperator()).isEqualTo(UPDATED_IS_OPERATOR);
    assertThat(testOrganizationImported.getTechnicalAccountFirstname()).isEqualTo(UPDATED_TECHNICAL_ACCOUNT_FIRSTNAME);
    assertThat(testOrganizationImported.getTechnicalAccountLastname()).isEqualTo(UPDATED_TECHNICAL_ACCOUNT_LASTNAME);
    assertThat(testOrganizationImported.getTechnicalAccountMail()).isEqualTo(UPDATED_TECHNICAL_ACCOUNT_MAIL);
    assertThat(testOrganizationImported.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    assertThat(testOrganizationImported.getOrgCreated()).isEqualTo(UPDATED_ORG_CREATED);
    assertThat(testOrganizationImported.getTechnicalAccountCreated()).isEqualTo(UPDATED_TECHNICAL_ACCOUNT_CREATED);
    assertThat(testOrganizationImported.getConsoCreated()).isEqualTo(UPDATED_CONSO_CREATED);
    assertThat(testOrganizationImported.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
  }

  @Test
  @Transactional
  void patchNonExistingOrganizationImported() throws Exception {
    int databaseSizeBeforeUpdate = organizationImportedRepository.findAll().size();
    organizationImported.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restOrganizationImportedMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, organizationImported.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(organizationImported))
      )
      .andExpect(status().isBadRequest());

    // Validate the OrganizationImported in the database
    List<OrganizationImported> organizationImportedList = organizationImportedRepository.findAll();
    assertThat(organizationImportedList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchOrganizationImported() throws Exception {
    int databaseSizeBeforeUpdate = organizationImportedRepository.findAll().size();
    organizationImported.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restOrganizationImportedMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(organizationImported))
      )
      .andExpect(status().isBadRequest());

    // Validate the OrganizationImported in the database
    List<OrganizationImported> organizationImportedList = organizationImportedRepository.findAll();
    assertThat(organizationImportedList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamOrganizationImported() throws Exception {
    int databaseSizeBeforeUpdate = organizationImportedRepository.findAll().size();
    organizationImported.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restOrganizationImportedMockMvc
      .perform(
        patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(organizationImported))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the OrganizationImported in the database
    List<OrganizationImported> organizationImportedList = organizationImportedRepository.findAll();
    assertThat(organizationImportedList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteOrganizationImported() throws Exception {
    // Initialize the database
    organizationImportedRepository.saveAndFlush(organizationImported);

    int databaseSizeBeforeDelete = organizationImportedRepository.findAll().size();

    // Delete the organizationImported
    restOrganizationImportedMockMvc
      .perform(delete(ENTITY_API_URL_ID, organizationImported.getId()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<OrganizationImported> organizationImportedList = organizationImportedRepository.findAll();
    assertThat(organizationImportedList).hasSize(databaseSizeBeforeDelete - 1);
  }
}
