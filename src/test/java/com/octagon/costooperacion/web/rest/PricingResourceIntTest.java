package com.octagon.costooperacion.web.rest;

import com.octagon.costooperacion.CostooperacionApp;

import com.octagon.costooperacion.domain.Pricing;
import com.octagon.costooperacion.repository.PricingRepository;
import com.octagon.costooperacion.service.PricingService;
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
import java.math.BigDecimal;
import java.util.List;


import static com.octagon.costooperacion.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PricingResource REST controller.
 *
 * @see PricingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CostooperacionApp.class)
public class PricingResourceIntTest {

    private static final Boolean DEFAULT_APROBADO = false;
    private static final Boolean UPDATED_APROBADO = true;

    private static final BigDecimal DEFAULT_CONDICION_MENOR_IGUAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_CONDICION_MENOR_IGUAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CONDICION_MAYOR_IGUAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_CONDICION_MAYOR_IGUAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CONDICION_MONTO_DESDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CONDICION_MONTO_DESDE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CONDICION_MONTO_HASTA = new BigDecimal(1);
    private static final BigDecimal UPDATED_CONDICION_MONTO_HASTA = new BigDecimal(2);

    @Autowired
    private PricingRepository pricingRepository;

    @Autowired
    private PricingService pricingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

//    @Autowired
//    private Validator validator;

    private MockMvc restPricingMockMvc;

    private Pricing pricing;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PricingResource pricingResource = new PricingResource(pricingService);
        this.restPricingMockMvc = MockMvcBuilders.standaloneSetup(pricingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
//            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pricing createEntity(EntityManager em) {
        Pricing pricing = new Pricing()
            .aprobado(DEFAULT_APROBADO)
            .condicionMenorIgual(DEFAULT_CONDICION_MENOR_IGUAL)
            .condicionMayorIgual(DEFAULT_CONDICION_MAYOR_IGUAL)
            .condicionMontoDesde(DEFAULT_CONDICION_MONTO_DESDE)
            .condicionMontoHasta(DEFAULT_CONDICION_MONTO_HASTA);
        return pricing;
    }

    @Before
    public void initTest() {
        pricing = createEntity(em);
    }

    @Test
    @Transactional
    public void createPricing() throws Exception {
        int databaseSizeBeforeCreate = pricingRepository.findAll().size();

        // Create the Pricing
        restPricingMockMvc.perform(post("/api/pricings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricing)))
            .andExpect(status().isCreated());

        // Validate the Pricing in the database
        List<Pricing> pricingList = pricingRepository.findAll();
        assertThat(pricingList).hasSize(databaseSizeBeforeCreate + 1);
        Pricing testPricing = pricingList.get(pricingList.size() - 1);
        assertThat(testPricing.isAprobado()).isEqualTo(DEFAULT_APROBADO);
        assertThat(testPricing.getCondicionMenorIgual()).isEqualTo(DEFAULT_CONDICION_MENOR_IGUAL);
        assertThat(testPricing.getCondicionMayorIgual()).isEqualTo(DEFAULT_CONDICION_MAYOR_IGUAL);
        assertThat(testPricing.getCondicionMontoDesde()).isEqualTo(DEFAULT_CONDICION_MONTO_DESDE);
        assertThat(testPricing.getCondicionMontoHasta()).isEqualTo(DEFAULT_CONDICION_MONTO_HASTA);
    }

    @Test
    @Transactional
    public void createPricingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pricingRepository.findAll().size();

        // Create the Pricing with an existing ID
        pricing.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPricingMockMvc.perform(post("/api/pricings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricing)))
            .andExpect(status().isBadRequest());

        // Validate the Pricing in the database
        List<Pricing> pricingList = pricingRepository.findAll();
        assertThat(pricingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPricings() throws Exception {
        // Initialize the database
        pricingRepository.saveAndFlush(pricing);

        // Get all the pricingList
        restPricingMockMvc.perform(get("/api/pricings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pricing.getId().intValue())))
            .andExpect(jsonPath("$.[*].aprobado").value(hasItem(DEFAULT_APROBADO.booleanValue())))
            .andExpect(jsonPath("$.[*].condicionMenorIgual").value(hasItem(DEFAULT_CONDICION_MENOR_IGUAL.intValue())))
            .andExpect(jsonPath("$.[*].condicionMayorIgual").value(hasItem(DEFAULT_CONDICION_MAYOR_IGUAL.intValue())))
            .andExpect(jsonPath("$.[*].condicionMontoDesde").value(hasItem(DEFAULT_CONDICION_MONTO_DESDE.intValue())))
            .andExpect(jsonPath("$.[*].condicionMontoHasta").value(hasItem(DEFAULT_CONDICION_MONTO_HASTA.intValue())));
    }
    
    @Test
    @Transactional
    public void getPricing() throws Exception {
        // Initialize the database
        pricingRepository.saveAndFlush(pricing);

        // Get the pricing
        restPricingMockMvc.perform(get("/api/pricings/{id}", pricing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pricing.getId().intValue()))
            .andExpect(jsonPath("$.aprobado").value(DEFAULT_APROBADO.booleanValue()))
            .andExpect(jsonPath("$.condicionMenorIgual").value(DEFAULT_CONDICION_MENOR_IGUAL.intValue()))
            .andExpect(jsonPath("$.condicionMayorIgual").value(DEFAULT_CONDICION_MAYOR_IGUAL.intValue()))
            .andExpect(jsonPath("$.condicionMontoDesde").value(DEFAULT_CONDICION_MONTO_DESDE.intValue()))
            .andExpect(jsonPath("$.condicionMontoHasta").value(DEFAULT_CONDICION_MONTO_HASTA.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPricing() throws Exception {
        // Get the pricing
        restPricingMockMvc.perform(get("/api/pricings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePricing() throws Exception {
        // Initialize the database
//        pricingService.save(pricing);

        int databaseSizeBeforeUpdate = pricingRepository.findAll().size();

        // Update the pricing
        Pricing updatedPricing = pricingRepository.findById(pricing.getId()).get();
        // Disconnect from session so that the updates on updatedPricing are not directly saved in db
        em.detach(updatedPricing);
        updatedPricing
            .aprobado(UPDATED_APROBADO)
            .condicionMenorIgual(UPDATED_CONDICION_MENOR_IGUAL)
            .condicionMayorIgual(UPDATED_CONDICION_MAYOR_IGUAL)
            .condicionMontoDesde(UPDATED_CONDICION_MONTO_DESDE)
            .condicionMontoHasta(UPDATED_CONDICION_MONTO_HASTA);

        restPricingMockMvc.perform(put("/api/pricings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPricing)))
            .andExpect(status().isOk());

        // Validate the Pricing in the database
        List<Pricing> pricingList = pricingRepository.findAll();
        assertThat(pricingList).hasSize(databaseSizeBeforeUpdate);
        Pricing testPricing = pricingList.get(pricingList.size() - 1);
        assertThat(testPricing.isAprobado()).isEqualTo(UPDATED_APROBADO);
        assertThat(testPricing.getCondicionMenorIgual()).isEqualTo(UPDATED_CONDICION_MENOR_IGUAL);
        assertThat(testPricing.getCondicionMayorIgual()).isEqualTo(UPDATED_CONDICION_MAYOR_IGUAL);
        assertThat(testPricing.getCondicionMontoDesde()).isEqualTo(UPDATED_CONDICION_MONTO_DESDE);
        assertThat(testPricing.getCondicionMontoHasta()).isEqualTo(UPDATED_CONDICION_MONTO_HASTA);
    }

    @Test
    @Transactional
    public void updateNonExistingPricing() throws Exception {
        int databaseSizeBeforeUpdate = pricingRepository.findAll().size();

        // Create the Pricing

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPricingMockMvc.perform(put("/api/pricings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricing)))
            .andExpect(status().isBadRequest());

        // Validate the Pricing in the database
        List<Pricing> pricingList = pricingRepository.findAll();
        assertThat(pricingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePricing() throws Exception {
        // Initialize the database
//        pricingService.save(pricing);

        int databaseSizeBeforeDelete = pricingRepository.findAll().size();

        // Delete the pricing
        restPricingMockMvc.perform(delete("/api/pricings/{id}", pricing.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pricing> pricingList = pricingRepository.findAll();
        assertThat(pricingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pricing.class);
        Pricing pricing1 = new Pricing();
        pricing1.setId(1L);
        Pricing pricing2 = new Pricing();
        pricing2.setId(pricing1.getId());
        assertThat(pricing1).isEqualTo(pricing2);
        pricing2.setId(2L);
        assertThat(pricing1).isNotEqualTo(pricing2);
        pricing1.setId(null);
        assertThat(pricing1).isNotEqualTo(pricing2);
    }
}
