package com.universign.universigncs.nosiax.admin.cpm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.nosiax.admin.cpm.IntegrationTest;
import com.universign.universigncs.nosiax.admin.cpm.domain.AdditionalKeys;
import com.universign.universigncs.nosiax.admin.cpm.repository.AdditionalKeysRepository;
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
 * Integration tests for the {@link AdditionalKeysResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdditionalKeysResourceIT {

  private static final String DEFAULT_KEY = "AAAAAAAAAA";
  private static final String UPDATED_KEY = "BBBBBBBBBB";

  private static final String DEFAULT_VALUE = "AAAAAAAAAA";
  private static final String UPDATED_VALUE = "BBBBBBBBBB";

  private static final String ENTITY_API_URL = "/api/additional-keys";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private AdditionalKeysRepository additionalKeysRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restAdditionalKeysMockMvc;

  private AdditionalKeys additionalKeys;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static AdditionalKeys createEntity(EntityManager em) {
    AdditionalKeys additionalKeys = new AdditionalKeys().key(DEFAULT_KEY).value(DEFAULT_VALUE);
    return additionalKeys;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static AdditionalKeys createUpdatedEntity(EntityManager em) {
    AdditionalKeys additionalKeys = new AdditionalKeys().key(UPDATED_KEY).value(UPDATED_VALUE);
    return additionalKeys;
  }

  @BeforeEach
  public void initTest() {
    additionalKeys = createEntity(em);
  }

  @Test
  @Transactional
  void createAdditionalKeys() throws Exception {
    int databaseSizeBeforeCreate = additionalKeysRepository.findAll().size();
    // Create the AdditionalKeys
    restAdditionalKeysMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(additionalKeys)))
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
  void createAdditionalKeysWithExistingId() throws Exception {
    // Create the AdditionalKeys with an existing ID
    additionalKeys.setId(1L);

    int databaseSizeBeforeCreate = additionalKeysRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restAdditionalKeysMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(additionalKeys)))
      .andExpect(status().isBadRequest());

    // Validate the AdditionalKeys in the database
    List<AdditionalKeys> additionalKeysList = additionalKeysRepository.findAll();
    assertThat(additionalKeysList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkKeyIsRequired() throws Exception {
    int databaseSizeBeforeTest = additionalKeysRepository.findAll().size();
    // set the field null
    additionalKeys.setKey(null);

    // Create the AdditionalKeys, which fails.

    restAdditionalKeysMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(additionalKeys)))
      .andExpect(status().isBadRequest());

    List<AdditionalKeys> additionalKeysList = additionalKeysRepository.findAll();
    assertThat(additionalKeysList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkValueIsRequired() throws Exception {
    int databaseSizeBeforeTest = additionalKeysRepository.findAll().size();
    // set the field null
    additionalKeys.setValue(null);

    // Create the AdditionalKeys, which fails.

    restAdditionalKeysMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(additionalKeys)))
      .andExpect(status().isBadRequest());

    List<AdditionalKeys> additionalKeysList = additionalKeysRepository.findAll();
    assertThat(additionalKeysList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllAdditionalKeys() throws Exception {
    // Initialize the database
    additionalKeysRepository.saveAndFlush(additionalKeys);

    // Get all the additionalKeysList
    restAdditionalKeysMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(additionalKeys.getId().intValue())))
      .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
      .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
  }

  @Test
  @Transactional
  void getAdditionalKeys() throws Exception {
    // Initialize the database
    additionalKeysRepository.saveAndFlush(additionalKeys);

    // Get the additionalKeys
    restAdditionalKeysMockMvc
      .perform(get(ENTITY_API_URL_ID, additionalKeys.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(additionalKeys.getId().intValue()))
      .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
      .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
  }

  @Test
  @Transactional
  void getNonExistingAdditionalKeys() throws Exception {
    // Get the additionalKeys
    restAdditionalKeysMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewAdditionalKeys() throws Exception {
    // Initialize the database
    additionalKeysRepository.saveAndFlush(additionalKeys);

    int databaseSizeBeforeUpdate = additionalKeysRepository.findAll().size();

    // Update the additionalKeys
    AdditionalKeys updatedAdditionalKeys = additionalKeysRepository.findById(additionalKeys.getId()).get();
    // Disconnect from session so that the updates on updatedAdditionalKeys are not directly saved in db
    em.detach(updatedAdditionalKeys);
    updatedAdditionalKeys.key(UPDATED_KEY).value(UPDATED_VALUE);

    restAdditionalKeysMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedAdditionalKeys.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedAdditionalKeys))
      )
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
  void putNonExistingAdditionalKeys() throws Exception {
    int databaseSizeBeforeUpdate = additionalKeysRepository.findAll().size();
    additionalKeys.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restAdditionalKeysMockMvc
      .perform(
        put(ENTITY_API_URL_ID, additionalKeys.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(additionalKeys))
      )
      .andExpect(status().isBadRequest());

    // Validate the AdditionalKeys in the database
    List<AdditionalKeys> additionalKeysList = additionalKeysRepository.findAll();
    assertThat(additionalKeysList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchAdditionalKeys() throws Exception {
    int databaseSizeBeforeUpdate = additionalKeysRepository.findAll().size();
    additionalKeys.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAdditionalKeysMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(additionalKeys))
      )
      .andExpect(status().isBadRequest());

    // Validate the AdditionalKeys in the database
    List<AdditionalKeys> additionalKeysList = additionalKeysRepository.findAll();
    assertThat(additionalKeysList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamAdditionalKeys() throws Exception {
    int databaseSizeBeforeUpdate = additionalKeysRepository.findAll().size();
    additionalKeys.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAdditionalKeysMockMvc
      .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(additionalKeys)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the AdditionalKeys in the database
    List<AdditionalKeys> additionalKeysList = additionalKeysRepository.findAll();
    assertThat(additionalKeysList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateAdditionalKeysWithPatch() throws Exception {
    // Initialize the database
    additionalKeysRepository.saveAndFlush(additionalKeys);

    int databaseSizeBeforeUpdate = additionalKeysRepository.findAll().size();

    // Update the additionalKeys using partial update
    AdditionalKeys partialUpdatedAdditionalKeys = new AdditionalKeys();
    partialUpdatedAdditionalKeys.setId(additionalKeys.getId());

    restAdditionalKeysMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedAdditionalKeys.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdditionalKeys))
      )
      .andExpect(status().isOk());

    // Validate the AdditionalKeys in the database
    List<AdditionalKeys> additionalKeysList = additionalKeysRepository.findAll();
    assertThat(additionalKeysList).hasSize(databaseSizeBeforeUpdate);
    AdditionalKeys testAdditionalKeys = additionalKeysList.get(additionalKeysList.size() - 1);
    assertThat(testAdditionalKeys.getKey()).isEqualTo(DEFAULT_KEY);
    assertThat(testAdditionalKeys.getValue()).isEqualTo(DEFAULT_VALUE);
  }

  @Test
  @Transactional
  void fullUpdateAdditionalKeysWithPatch() throws Exception {
    // Initialize the database
    additionalKeysRepository.saveAndFlush(additionalKeys);

    int databaseSizeBeforeUpdate = additionalKeysRepository.findAll().size();

    // Update the additionalKeys using partial update
    AdditionalKeys partialUpdatedAdditionalKeys = new AdditionalKeys();
    partialUpdatedAdditionalKeys.setId(additionalKeys.getId());

    partialUpdatedAdditionalKeys.key(UPDATED_KEY).value(UPDATED_VALUE);

    restAdditionalKeysMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedAdditionalKeys.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdditionalKeys))
      )
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
  void patchNonExistingAdditionalKeys() throws Exception {
    int databaseSizeBeforeUpdate = additionalKeysRepository.findAll().size();
    additionalKeys.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restAdditionalKeysMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, additionalKeys.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(additionalKeys))
      )
      .andExpect(status().isBadRequest());

    // Validate the AdditionalKeys in the database
    List<AdditionalKeys> additionalKeysList = additionalKeysRepository.findAll();
    assertThat(additionalKeysList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchAdditionalKeys() throws Exception {
    int databaseSizeBeforeUpdate = additionalKeysRepository.findAll().size();
    additionalKeys.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAdditionalKeysMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(additionalKeys))
      )
      .andExpect(status().isBadRequest());

    // Validate the AdditionalKeys in the database
    List<AdditionalKeys> additionalKeysList = additionalKeysRepository.findAll();
    assertThat(additionalKeysList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamAdditionalKeys() throws Exception {
    int databaseSizeBeforeUpdate = additionalKeysRepository.findAll().size();
    additionalKeys.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAdditionalKeysMockMvc
      .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(additionalKeys)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the AdditionalKeys in the database
    List<AdditionalKeys> additionalKeysList = additionalKeysRepository.findAll();
    assertThat(additionalKeysList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteAdditionalKeys() throws Exception {
    // Initialize the database
    additionalKeysRepository.saveAndFlush(additionalKeys);

    int databaseSizeBeforeDelete = additionalKeysRepository.findAll().size();

    // Delete the additionalKeys
    restAdditionalKeysMockMvc
      .perform(delete(ENTITY_API_URL_ID, additionalKeys.getId()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<AdditionalKeys> additionalKeysList = additionalKeysRepository.findAll();
    assertThat(additionalKeysList).hasSize(databaseSizeBeforeDelete - 1);
  }
}
