package com.octagon.costooperacion.web.rest;

import com.octagon.costooperacion.CostooperacionApp;

import com.octagon.costooperacion.domain.PricingHistory;
import com.octagon.costooperacion.repository.PricingHistoryRepository;
import com.octagon.costooperacion.service.PricingHistoryService;
import com.octagon.costooperacion.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.octagon.costooperacion.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PricingHistoryResource REST controller.
 *
 * @see PricingHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CostooperacionApp.class)
public class PricingHistoryResourceIntTest {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOTA_ACTUALIZACION = "AAAAAAAAAA";
    private static final String UPDATED_NOTA_ACTUALIZACION = "BBBBBBBBBB";

    @Autowired
    private PricingHistoryRepository pricingHistoryRepository;

    @Autowired
    private PricingHistoryService pricingHistoryService;

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

    private MockMvc restPricingHistoryMockMvc;

    private PricingHistory pricingHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PricingHistoryResource pricingHistoryResource = new PricingHistoryResource(pricingHistoryService);
        this.restPricingHistoryMockMvc = MockMvcBuilders.standaloneSetup(pricingHistoryResource)
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
    public static PricingHistory createEntity(EntityManager em) {
        PricingHistory pricingHistory = new PricingHistory()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .notaActualizacion(DEFAULT_NOTA_ACTUALIZACION);
        return pricingHistory;
    }

    @Before
    public void initTest() {
        pricingHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createPricingHistory() throws Exception {
        int databaseSizeBeforeCreate = pricingHistoryRepository.findAll().size();

        // Create the PricingHistory
        restPricingHistoryMockMvc.perform(post("/api/pricing-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricingHistory)))
            .andExpect(status().isCreated());

        // Validate the PricingHistory in the database
        List<PricingHistory> pricingHistoryList = pricingHistoryRepository.findAll();
        assertThat(pricingHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        PricingHistory testPricingHistory = pricingHistoryList.get(pricingHistoryList.size() - 1);
        assertThat(testPricingHistory.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPricingHistory.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPricingHistory.getNotaActualizacion()).isEqualTo(DEFAULT_NOTA_ACTUALIZACION);
    }

    @Test
    @Transactional
    public void createPricingHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pricingHistoryRepository.findAll().size();

        // Create the PricingHistory with an existing ID
        pricingHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPricingHistoryMockMvc.perform(post("/api/pricing-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricingHistory)))
            .andExpect(status().isBadRequest());

        // Validate the PricingHistory in the database
        List<PricingHistory> pricingHistoryList = pricingHistoryRepository.findAll();
        assertThat(pricingHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPricingHistories() throws Exception {
        // Initialize the database
        pricingHistoryRepository.saveAndFlush(pricingHistory);

        // Get all the pricingHistoryList
        restPricingHistoryMockMvc.perform(get("/api/pricing-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pricingHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].notaActualizacion").value(hasItem(DEFAULT_NOTA_ACTUALIZACION.toString())));
    }
    
    @Test
    @Transactional
    public void getPricingHistory() throws Exception {
        // Initialize the database
        pricingHistoryRepository.saveAndFlush(pricingHistory);

        // Get the pricingHistory
        restPricingHistoryMockMvc.perform(get("/api/pricing-histories/{id}", pricingHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pricingHistory.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.notaActualizacion").value(DEFAULT_NOTA_ACTUALIZACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPricingHistory() throws Exception {
        // Get the pricingHistory
        restPricingHistoryMockMvc.perform(get("/api/pricing-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePricingHistory() throws Exception {
        // Initialize the database
        pricingHistoryService.save(pricingHistory);

        int databaseSizeBeforeUpdate = pricingHistoryRepository.findAll().size();

        // Update the pricingHistory
        PricingHistory updatedPricingHistory = pricingHistoryRepository.findById(pricingHistory.getId()).get();
        // Disconnect from session so that the updates on updatedPricingHistory are not directly saved in db
        em.detach(updatedPricingHistory);
        updatedPricingHistory
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .notaActualizacion(UPDATED_NOTA_ACTUALIZACION);

        restPricingHistoryMockMvc.perform(put("/api/pricing-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPricingHistory)))
            .andExpect(status().isOk());

        // Validate the PricingHistory in the database
        List<PricingHistory> pricingHistoryList = pricingHistoryRepository.findAll();
        assertThat(pricingHistoryList).hasSize(databaseSizeBeforeUpdate);
        PricingHistory testPricingHistory = pricingHistoryList.get(pricingHistoryList.size() - 1);
        assertThat(testPricingHistory.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPricingHistory.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPricingHistory.getNotaActualizacion()).isEqualTo(UPDATED_NOTA_ACTUALIZACION);
    }

    @Test
    @Transactional
    public void updateNonExistingPricingHistory() throws Exception {
        int databaseSizeBeforeUpdate = pricingHistoryRepository.findAll().size();

        // Create the PricingHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPricingHistoryMockMvc.perform(put("/api/pricing-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricingHistory)))
            .andExpect(status().isBadRequest());

        // Validate the PricingHistory in the database
        List<PricingHistory> pricingHistoryList = pricingHistoryRepository.findAll();
        assertThat(pricingHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePricingHistory() throws Exception {
        // Initialize the database
        pricingHistoryService.save(pricingHistory);

        int databaseSizeBeforeDelete = pricingHistoryRepository.findAll().size();

        // Delete the pricingHistory
        restPricingHistoryMockMvc.perform(delete("/api/pricing-histories/{id}", pricingHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PricingHistory> pricingHistoryList = pricingHistoryRepository.findAll();
        assertThat(pricingHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PricingHistory.class);
        PricingHistory pricingHistory1 = new PricingHistory();
        pricingHistory1.setId(1L);
        PricingHistory pricingHistory2 = new PricingHistory();
        pricingHistory2.setId(pricingHistory1.getId());
        assertThat(pricingHistory1).isEqualTo(pricingHistory2);
        pricingHistory2.setId(2L);
        assertThat(pricingHistory1).isNotEqualTo(pricingHistory2);
        pricingHistory1.setId(null);
        assertThat(pricingHistory1).isNotEqualTo(pricingHistory2);
    }
}
