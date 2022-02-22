package com.universign.universigncs.nosiax.admin.cpm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.nosiax.admin.cpm.IntegrationTest;
import com.universign.universigncs.nosiax.admin.cpm.domain.Voucher;
import com.universign.universigncs.nosiax.admin.cpm.repository.VoucherRepository;
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
 * Integration tests for the {@link VoucherResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VoucherResourceIT {

  private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
  private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

  private static final String DEFAULT_KEY = "AAAAAAAAAA";
  private static final String UPDATED_KEY = "BBBBBBBBBB";

  private static final String ENTITY_API_URL = "/api/vouchers";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private VoucherRepository voucherRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restVoucherMockMvc;

  private Voucher voucher;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Voucher createEntity(EntityManager em) {
    Voucher voucher = new Voucher().fileName(DEFAULT_FILE_NAME).key(DEFAULT_KEY);
    return voucher;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Voucher createUpdatedEntity(EntityManager em) {
    Voucher voucher = new Voucher().fileName(UPDATED_FILE_NAME).key(UPDATED_KEY);
    return voucher;
  }

  @BeforeEach
  public void initTest() {
    voucher = createEntity(em);
  }

  @Test
  @Transactional
  void createVoucher() throws Exception {
    int databaseSizeBeforeCreate = voucherRepository.findAll().size();
    // Create the Voucher
    restVoucherMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucher)))
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
  void createVoucherWithExistingId() throws Exception {
    // Create the Voucher with an existing ID
    voucher.setId(1L);

    int databaseSizeBeforeCreate = voucherRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restVoucherMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucher)))
      .andExpect(status().isBadRequest());

    // Validate the Voucher in the database
    List<Voucher> voucherList = voucherRepository.findAll();
    assertThat(voucherList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkFileNameIsRequired() throws Exception {
    int databaseSizeBeforeTest = voucherRepository.findAll().size();
    // set the field null
    voucher.setFileName(null);

    // Create the Voucher, which fails.

    restVoucherMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucher)))
      .andExpect(status().isBadRequest());

    List<Voucher> voucherList = voucherRepository.findAll();
    assertThat(voucherList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkKeyIsRequired() throws Exception {
    int databaseSizeBeforeTest = voucherRepository.findAll().size();
    // set the field null
    voucher.setKey(null);

    // Create the Voucher, which fails.

    restVoucherMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucher)))
      .andExpect(status().isBadRequest());

    List<Voucher> voucherList = voucherRepository.findAll();
    assertThat(voucherList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllVouchers() throws Exception {
    // Initialize the database
    voucherRepository.saveAndFlush(voucher);

    // Get all the voucherList
    restVoucherMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(voucher.getId().intValue())))
      .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
      .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)));
  }

  @Test
  @Transactional
  void getVoucher() throws Exception {
    // Initialize the database
    voucherRepository.saveAndFlush(voucher);

    // Get the voucher
    restVoucherMockMvc
      .perform(get(ENTITY_API_URL_ID, voucher.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(voucher.getId().intValue()))
      .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
      .andExpect(jsonPath("$.key").value(DEFAULT_KEY));
  }

  @Test
  @Transactional
  void getNonExistingVoucher() throws Exception {
    // Get the voucher
    restVoucherMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewVoucher() throws Exception {
    // Initialize the database
    voucherRepository.saveAndFlush(voucher);

    int databaseSizeBeforeUpdate = voucherRepository.findAll().size();

    // Update the voucher
    Voucher updatedVoucher = voucherRepository.findById(voucher.getId()).get();
    // Disconnect from session so that the updates on updatedVoucher are not directly saved in db
    em.detach(updatedVoucher);
    updatedVoucher.fileName(UPDATED_FILE_NAME).key(UPDATED_KEY);

    restVoucherMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedVoucher.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedVoucher))
      )
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
  void putNonExistingVoucher() throws Exception {
    int databaseSizeBeforeUpdate = voucherRepository.findAll().size();
    voucher.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restVoucherMockMvc
      .perform(
        put(ENTITY_API_URL_ID, voucher.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucher))
      )
      .andExpect(status().isBadRequest());

    // Validate the Voucher in the database
    List<Voucher> voucherList = voucherRepository.findAll();
    assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchVoucher() throws Exception {
    int databaseSizeBeforeUpdate = voucherRepository.findAll().size();
    voucher.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restVoucherMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(voucher))
      )
      .andExpect(status().isBadRequest());

    // Validate the Voucher in the database
    List<Voucher> voucherList = voucherRepository.findAll();
    assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamVoucher() throws Exception {
    int databaseSizeBeforeUpdate = voucherRepository.findAll().size();
    voucher.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restVoucherMockMvc
      .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucher)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the Voucher in the database
    List<Voucher> voucherList = voucherRepository.findAll();
    assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateVoucherWithPatch() throws Exception {
    // Initialize the database
    voucherRepository.saveAndFlush(voucher);

    int databaseSizeBeforeUpdate = voucherRepository.findAll().size();

    // Update the voucher using partial update
    Voucher partialUpdatedVoucher = new Voucher();
    partialUpdatedVoucher.setId(voucher.getId());

    partialUpdatedVoucher.key(UPDATED_KEY);

    restVoucherMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedVoucher.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoucher))
      )
      .andExpect(status().isOk());

    // Validate the Voucher in the database
    List<Voucher> voucherList = voucherRepository.findAll();
    assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
    Voucher testVoucher = voucherList.get(voucherList.size() - 1);
    assertThat(testVoucher.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
    assertThat(testVoucher.getKey()).isEqualTo(UPDATED_KEY);
  }

  @Test
  @Transactional
  void fullUpdateVoucherWithPatch() throws Exception {
    // Initialize the database
    voucherRepository.saveAndFlush(voucher);

    int databaseSizeBeforeUpdate = voucherRepository.findAll().size();

    // Update the voucher using partial update
    Voucher partialUpdatedVoucher = new Voucher();
    partialUpdatedVoucher.setId(voucher.getId());

    partialUpdatedVoucher.fileName(UPDATED_FILE_NAME).key(UPDATED_KEY);

    restVoucherMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedVoucher.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoucher))
      )
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
  void patchNonExistingVoucher() throws Exception {
    int databaseSizeBeforeUpdate = voucherRepository.findAll().size();
    voucher.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restVoucherMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, voucher.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(voucher))
      )
      .andExpect(status().isBadRequest());

    // Validate the Voucher in the database
    List<Voucher> voucherList = voucherRepository.findAll();
    assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchVoucher() throws Exception {
    int databaseSizeBeforeUpdate = voucherRepository.findAll().size();
    voucher.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restVoucherMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(voucher))
      )
      .andExpect(status().isBadRequest());

    // Validate the Voucher in the database
    List<Voucher> voucherList = voucherRepository.findAll();
    assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamVoucher() throws Exception {
    int databaseSizeBeforeUpdate = voucherRepository.findAll().size();
    voucher.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restVoucherMockMvc
      .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(voucher)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the Voucher in the database
    List<Voucher> voucherList = voucherRepository.findAll();
    assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteVoucher() throws Exception {
    // Initialize the database
    voucherRepository.saveAndFlush(voucher);

    int databaseSizeBeforeDelete = voucherRepository.findAll().size();

    // Delete the voucher
    restVoucherMockMvc
      .perform(delete(ENTITY_API_URL_ID, voucher.getId()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<Voucher> voucherList = voucherRepository.findAll();
    assertThat(voucherList).hasSize(databaseSizeBeforeDelete - 1);
  }
}
