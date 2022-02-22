package com.universign.universigncs.nosiax.admin.cpm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.nosiax.admin.cpm.IntegrationTest;
import com.universign.universigncs.nosiax.admin.cpm.domain.ImportOrganizations;
import com.universign.universigncs.nosiax.admin.cpm.repository.ImportOrganizationsRepository;
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
 * Integration tests for the {@link ImportOrganizationsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ImportOrganizationsResourceIT {

  private static final String DEFAULT_ORG_MASTER_ID = "AAAAAAAAAA";
  private static final String UPDATED_ORG_MASTER_ID = "BBBBBBBBBB";

  private static final String DEFAULT_ORG_NAME = "AAAAAAAAAA";
  private static final String UPDATED_ORG_NAME = "BBBBBBBBBB";

  private static final String DEFAULT_PARTENAIRE = "AAAAAAAAAA";
  private static final String UPDATED_PARTENAIRE = "BBBBBBBBBB";

  private static final String DEFAULT_PROFILE = "AAAAAAAAAA";
  private static final String UPDATED_PROFILE = "BBBBBBBBBB";

  private static final LocalDate DEFAULT_LAUNCH_CREATION_DATE = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_LAUNCH_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

  private static final String ENTITY_API_URL = "/api/import-organizations";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private ImportOrganizationsRepository importOrganizationsRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restImportOrganizationsMockMvc;

  private ImportOrganizations importOrganizations;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static ImportOrganizations createEntity(EntityManager em) {
    ImportOrganizations importOrganizations = new ImportOrganizations()
      .orgMasterId(DEFAULT_ORG_MASTER_ID)
      .orgName(DEFAULT_ORG_NAME)
      .partenaire(DEFAULT_PARTENAIRE)
      .profile(DEFAULT_PROFILE)
      .launchCreationDate(DEFAULT_LAUNCH_CREATION_DATE);
    return importOrganizations;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static ImportOrganizations createUpdatedEntity(EntityManager em) {
    ImportOrganizations importOrganizations = new ImportOrganizations()
      .orgMasterId(UPDATED_ORG_MASTER_ID)
      .orgName(UPDATED_ORG_NAME)
      .partenaire(UPDATED_PARTENAIRE)
      .profile(UPDATED_PROFILE)
      .launchCreationDate(UPDATED_LAUNCH_CREATION_DATE);
    return importOrganizations;
  }

  @BeforeEach
  public void initTest() {
    importOrganizations = createEntity(em);
  }

  @Test
  @Transactional
  void createImportOrganizations() throws Exception {
    int databaseSizeBeforeCreate = importOrganizationsRepository.findAll().size();
    // Create the ImportOrganizations
    restImportOrganizationsMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(importOrganizations)))
      .andExpect(status().isCreated());

    // Validate the ImportOrganizations in the database
    List<ImportOrganizations> importOrganizationsList = importOrganizationsRepository.findAll();
    assertThat(importOrganizationsList).hasSize(databaseSizeBeforeCreate + 1);
    ImportOrganizations testImportOrganizations = importOrganizationsList.get(importOrganizationsList.size() - 1);
    assertThat(testImportOrganizations.getOrgMasterId()).isEqualTo(DEFAULT_ORG_MASTER_ID);
    assertThat(testImportOrganizations.getOrgName()).isEqualTo(DEFAULT_ORG_NAME);
    assertThat(testImportOrganizations.getPartenaire()).isEqualTo(DEFAULT_PARTENAIRE);
    assertThat(testImportOrganizations.getProfile()).isEqualTo(DEFAULT_PROFILE);
    assertThat(testImportOrganizations.getLaunchCreationDate()).isEqualTo(DEFAULT_LAUNCH_CREATION_DATE);
  }

  @Test
  @Transactional
  void createImportOrganizationsWithExistingId() throws Exception {
    // Create the ImportOrganizations with an existing ID
    importOrganizations.setId(1L);

    int databaseSizeBeforeCreate = importOrganizationsRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restImportOrganizationsMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(importOrganizations)))
      .andExpect(status().isBadRequest());

    // Validate the ImportOrganizations in the database
    List<ImportOrganizations> importOrganizationsList = importOrganizationsRepository.findAll();
    assertThat(importOrganizationsList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void getAllImportOrganizations() throws Exception {
    // Initialize the database
    importOrganizationsRepository.saveAndFlush(importOrganizations);

    // Get all the importOrganizationsList
    restImportOrganizationsMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(importOrganizations.getId().intValue())))
      .andExpect(jsonPath("$.[*].orgMasterId").value(hasItem(DEFAULT_ORG_MASTER_ID)))
      .andExpect(jsonPath("$.[*].orgName").value(hasItem(DEFAULT_ORG_NAME)))
      .andExpect(jsonPath("$.[*].partenaire").value(hasItem(DEFAULT_PARTENAIRE)))
      .andExpect(jsonPath("$.[*].profile").value(hasItem(DEFAULT_PROFILE)))
      .andExpect(jsonPath("$.[*].launchCreationDate").value(hasItem(DEFAULT_LAUNCH_CREATION_DATE.toString())));
  }

  @Test
  @Transactional
  void getImportOrganizations() throws Exception {
    // Initialize the database
    importOrganizationsRepository.saveAndFlush(importOrganizations);

    // Get the importOrganizations
    restImportOrganizationsMockMvc
      .perform(get(ENTITY_API_URL_ID, importOrganizations.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(importOrganizations.getId().intValue()))
      .andExpect(jsonPath("$.orgMasterId").value(DEFAULT_ORG_MASTER_ID))
      .andExpect(jsonPath("$.orgName").value(DEFAULT_ORG_NAME))
      .andExpect(jsonPath("$.partenaire").value(DEFAULT_PARTENAIRE))
      .andExpect(jsonPath("$.profile").value(DEFAULT_PROFILE))
      .andExpect(jsonPath("$.launchCreationDate").value(DEFAULT_LAUNCH_CREATION_DATE.toString()));
  }

  @Test
  @Transactional
  void getNonExistingImportOrganizations() throws Exception {
    // Get the importOrganizations
    restImportOrganizationsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewImportOrganizations() throws Exception {
    // Initialize the database
    importOrganizationsRepository.saveAndFlush(importOrganizations);

    int databaseSizeBeforeUpdate = importOrganizationsRepository.findAll().size();

    // Update the importOrganizations
    ImportOrganizations updatedImportOrganizations = importOrganizationsRepository.findById(importOrganizations.getId()).get();
    // Disconnect from session so that the updates on updatedImportOrganizations are not directly saved in db
    em.detach(updatedImportOrganizations);
    updatedImportOrganizations
      .orgMasterId(UPDATED_ORG_MASTER_ID)
      .orgName(UPDATED_ORG_NAME)
      .partenaire(UPDATED_PARTENAIRE)
      .profile(UPDATED_PROFILE)
      .launchCreationDate(UPDATED_LAUNCH_CREATION_DATE);

    restImportOrganizationsMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedImportOrganizations.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedImportOrganizations))
      )
      .andExpect(status().isOk());

    // Validate the ImportOrganizations in the database
    List<ImportOrganizations> importOrganizationsList = importOrganizationsRepository.findAll();
    assertThat(importOrganizationsList).hasSize(databaseSizeBeforeUpdate);
    ImportOrganizations testImportOrganizations = importOrganizationsList.get(importOrganizationsList.size() - 1);
    assertThat(testImportOrganizations.getOrgMasterId()).isEqualTo(UPDATED_ORG_MASTER_ID);
    assertThat(testImportOrganizations.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
    assertThat(testImportOrganizations.getPartenaire()).isEqualTo(UPDATED_PARTENAIRE);
    assertThat(testImportOrganizations.getProfile()).isEqualTo(UPDATED_PROFILE);
    assertThat(testImportOrganizations.getLaunchCreationDate()).isEqualTo(UPDATED_LAUNCH_CREATION_DATE);
  }

  @Test
  @Transactional
  void putNonExistingImportOrganizations() throws Exception {
    int databaseSizeBeforeUpdate = importOrganizationsRepository.findAll().size();
    importOrganizations.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restImportOrganizationsMockMvc
      .perform(
        put(ENTITY_API_URL_ID, importOrganizations.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(importOrganizations))
      )
      .andExpect(status().isBadRequest());

    // Validate the ImportOrganizations in the database
    List<ImportOrganizations> importOrganizationsList = importOrganizationsRepository.findAll();
    assertThat(importOrganizationsList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchImportOrganizations() throws Exception {
    int databaseSizeBeforeUpdate = importOrganizationsRepository.findAll().size();
    importOrganizations.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restImportOrganizationsMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(importOrganizations))
      )
      .andExpect(status().isBadRequest());

    // Validate the ImportOrganizations in the database
    List<ImportOrganizations> importOrganizationsList = importOrganizationsRepository.findAll();
    assertThat(importOrganizationsList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamImportOrganizations() throws Exception {
    int databaseSizeBeforeUpdate = importOrganizationsRepository.findAll().size();
    importOrganizations.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restImportOrganizationsMockMvc
      .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(importOrganizations)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the ImportOrganizations in the database
    List<ImportOrganizations> importOrganizationsList = importOrganizationsRepository.findAll();
    assertThat(importOrganizationsList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateImportOrganizationsWithPatch() throws Exception {
    // Initialize the database
    importOrganizationsRepository.saveAndFlush(importOrganizations);

    int databaseSizeBeforeUpdate = importOrganizationsRepository.findAll().size();

    // Update the importOrganizations using partial update
    ImportOrganizations partialUpdatedImportOrganizations = new ImportOrganizations();
    partialUpdatedImportOrganizations.setId(importOrganizations.getId());

    partialUpdatedImportOrganizations.orgName(UPDATED_ORG_NAME).partenaire(UPDATED_PARTENAIRE).profile(UPDATED_PROFILE);

    restImportOrganizationsMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedImportOrganizations.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedImportOrganizations))
      )
      .andExpect(status().isOk());

    // Validate the ImportOrganizations in the database
    List<ImportOrganizations> importOrganizationsList = importOrganizationsRepository.findAll();
    assertThat(importOrganizationsList).hasSize(databaseSizeBeforeUpdate);
    ImportOrganizations testImportOrganizations = importOrganizationsList.get(importOrganizationsList.size() - 1);
    assertThat(testImportOrganizations.getOrgMasterId()).isEqualTo(DEFAULT_ORG_MASTER_ID);
    assertThat(testImportOrganizations.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
    assertThat(testImportOrganizations.getPartenaire()).isEqualTo(UPDATED_PARTENAIRE);
    assertThat(testImportOrganizations.getProfile()).isEqualTo(UPDATED_PROFILE);
    assertThat(testImportOrganizations.getLaunchCreationDate()).isEqualTo(DEFAULT_LAUNCH_CREATION_DATE);
  }

  @Test
  @Transactional
  void fullUpdateImportOrganizationsWithPatch() throws Exception {
    // Initialize the database
    importOrganizationsRepository.saveAndFlush(importOrganizations);

    int databaseSizeBeforeUpdate = importOrganizationsRepository.findAll().size();

    // Update the importOrganizations using partial update
    ImportOrganizations partialUpdatedImportOrganizations = new ImportOrganizations();
    partialUpdatedImportOrganizations.setId(importOrganizations.getId());

    partialUpdatedImportOrganizations
      .orgMasterId(UPDATED_ORG_MASTER_ID)
      .orgName(UPDATED_ORG_NAME)
      .partenaire(UPDATED_PARTENAIRE)
      .profile(UPDATED_PROFILE)
      .launchCreationDate(UPDATED_LAUNCH_CREATION_DATE);

    restImportOrganizationsMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedImportOrganizations.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedImportOrganizations))
      )
      .andExpect(status().isOk());

    // Validate the ImportOrganizations in the database
    List<ImportOrganizations> importOrganizationsList = importOrganizationsRepository.findAll();
    assertThat(importOrganizationsList).hasSize(databaseSizeBeforeUpdate);
    ImportOrganizations testImportOrganizations = importOrganizationsList.get(importOrganizationsList.size() - 1);
    assertThat(testImportOrganizations.getOrgMasterId()).isEqualTo(UPDATED_ORG_MASTER_ID);
    assertThat(testImportOrganizations.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
    assertThat(testImportOrganizations.getPartenaire()).isEqualTo(UPDATED_PARTENAIRE);
    assertThat(testImportOrganizations.getProfile()).isEqualTo(UPDATED_PROFILE);
    assertThat(testImportOrganizations.getLaunchCreationDate()).isEqualTo(UPDATED_LAUNCH_CREATION_DATE);
  }

  @Test
  @Transactional
  void patchNonExistingImportOrganizations() throws Exception {
    int databaseSizeBeforeUpdate = importOrganizationsRepository.findAll().size();
    importOrganizations.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restImportOrganizationsMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, importOrganizations.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(importOrganizations))
      )
      .andExpect(status().isBadRequest());

    // Validate the ImportOrganizations in the database
    List<ImportOrganizations> importOrganizationsList = importOrganizationsRepository.findAll();
    assertThat(importOrganizationsList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchImportOrganizations() throws Exception {
    int databaseSizeBeforeUpdate = importOrganizationsRepository.findAll().size();
    importOrganizations.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restImportOrganizationsMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(importOrganizations))
      )
      .andExpect(status().isBadRequest());

    // Validate the ImportOrganizations in the database
    List<ImportOrganizations> importOrganizationsList = importOrganizationsRepository.findAll();
    assertThat(importOrganizationsList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamImportOrganizations() throws Exception {
    int databaseSizeBeforeUpdate = importOrganizationsRepository.findAll().size();
    importOrganizations.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restImportOrganizationsMockMvc
      .perform(
        patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(importOrganizations))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the ImportOrganizations in the database
    List<ImportOrganizations> importOrganizationsList = importOrganizationsRepository.findAll();
    assertThat(importOrganizationsList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteImportOrganizations() throws Exception {
    // Initialize the database
    importOrganizationsRepository.saveAndFlush(importOrganizations);

    int databaseSizeBeforeDelete = importOrganizationsRepository.findAll().size();

    // Delete the importOrganizations
    restImportOrganizationsMockMvc
      .perform(delete(ENTITY_API_URL_ID, importOrganizations.getId()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<ImportOrganizations> importOrganizationsList = importOrganizationsRepository.findAll();
    assertThat(importOrganizationsList).hasSize(databaseSizeBeforeDelete - 1);
  }
}
