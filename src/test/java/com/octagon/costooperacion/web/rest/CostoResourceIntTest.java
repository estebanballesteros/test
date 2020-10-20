package com.octagon.costooperacion.web.rest;

import com.octagon.costooperacion.CostooperacionApp;

import com.octagon.costooperacion.domain.Costo;
import com.octagon.costooperacion.repository.CostoRepository;
import com.octagon.costooperacion.service.CostoService;
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
 * Test class for the CostoResource REST controller.
 *
 * @see //CostoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CostooperacionApp.class)
public class CostoResourceIntTest {

    private static final BigDecimal DEFAULT_MONTO_DOLARES = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTO_DOLARES = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PORCENTAJE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PORCENTAJE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_IVA = new BigDecimal(1);
    private static final BigDecimal UPDATED_IVA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_DOLARES = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_DOLARES = new BigDecimal(2);

    @Autowired
    private CostoRepository costoRepository;

    @Autowired
    private CostoService costoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    //@Autowired
    //private Validator validator;

    private MockMvc restCostoMockMvc;

    private Costo costo;

    @Before
    public void setup() {
        /*MockitoAnnotations.initMocks(this);
        final CostoResource costoResource = new CostoResource(costoService);
        this.restCostoMockMvc = MockMvcBuilders.standaloneSetup(costoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();*/
            //.setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Costo createEntity(EntityManager em) {
        Costo costo = new Costo()
            .montoDolares(DEFAULT_MONTO_DOLARES)
            .porcentaje(DEFAULT_PORCENTAJE)
            .iva(DEFAULT_IVA)
            .totalDolares(DEFAULT_TOTAL_DOLARES);
        return costo;
    }

    @Before
    public void initTest() {
        costo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCosto() throws Exception {
        int databaseSizeBeforeCreate = costoRepository.findAll().size();

        // Create the Costo
        restCostoMockMvc.perform(post("/api/costos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costo)))
            .andExpect(status().isCreated());

        // Validate the Costo in the database
        List<Costo> costoList = costoRepository.findAll();
        assertThat(costoList).hasSize(databaseSizeBeforeCreate + 1);
        Costo testCosto = costoList.get(costoList.size() - 1);
        assertThat(testCosto.getMontoDolares()).isEqualTo(DEFAULT_MONTO_DOLARES);
        assertThat(testCosto.getPorcentaje()).isEqualTo(DEFAULT_PORCENTAJE);
        assertThat(testCosto.getIva()).isEqualTo(DEFAULT_IVA);
        assertThat(testCosto.getTotalDolares()).isEqualTo(DEFAULT_TOTAL_DOLARES);
    }

    @Test
    @Transactional
    public void createCostoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = costoRepository.findAll().size();

        // Create the Costo with an existing ID
        costo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCostoMockMvc.perform(post("/api/costos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costo)))
            .andExpect(status().isBadRequest());

        // Validate the Costo in the database
        List<Costo> costoList = costoRepository.findAll();
        assertThat(costoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCostos() throws Exception {
        // Initialize the database
        costoRepository.saveAndFlush(costo);

        // Get all the costoList
        restCostoMockMvc.perform(get("/api/costos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(costo.getId().intValue())))
            .andExpect(jsonPath("$.[*].montoDolares").value(hasItem(DEFAULT_MONTO_DOLARES.intValue())))
            .andExpect(jsonPath("$.[*].porcentaje").value(hasItem(DEFAULT_PORCENTAJE.intValue())))
            .andExpect(jsonPath("$.[*].iva").value(hasItem(DEFAULT_IVA.intValue())))
            .andExpect(jsonPath("$.[*].totalDolares").value(hasItem(DEFAULT_TOTAL_DOLARES.intValue())));
    }
    
    @Test
    @Transactional
    public void getCosto() throws Exception {
        // Initialize the database
        costoRepository.saveAndFlush(costo);

        // Get the costo
        restCostoMockMvc.perform(get("/api/costos/{id}", costo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(costo.getId().intValue()))
            .andExpect(jsonPath("$.montoDolares").value(DEFAULT_MONTO_DOLARES.intValue()))
            .andExpect(jsonPath("$.porcentaje").value(DEFAULT_PORCENTAJE.intValue()))
            .andExpect(jsonPath("$.iva").value(DEFAULT_IVA.intValue()))
            .andExpect(jsonPath("$.totalDolares").value(DEFAULT_TOTAL_DOLARES.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCosto() throws Exception {
        // Get the costo
        restCostoMockMvc.perform(get("/api/costos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCosto() throws Exception {
        // Initialize the database
        // costoService.save(costo);

        int databaseSizeBeforeUpdate = costoRepository.findAll().size();

        // Update the costo
        Costo updatedCosto = costoRepository.findById(costo.getId()).get();
        // Disconnect from session so that the updates on updatedCosto are not directly saved in db
        em.detach(updatedCosto);
        updatedCosto
            .montoDolares(UPDATED_MONTO_DOLARES)
            .porcentaje(UPDATED_PORCENTAJE)
            .iva(UPDATED_IVA)
            .totalDolares(UPDATED_TOTAL_DOLARES);

        restCostoMockMvc.perform(put("/api/costos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCosto)))
            .andExpect(status().isOk());

        // Validate the Costo in the database
        List<Costo> costoList = costoRepository.findAll();
        assertThat(costoList).hasSize(databaseSizeBeforeUpdate);
        Costo testCosto = costoList.get(costoList.size() - 1);
        assertThat(testCosto.getMontoDolares()).isEqualTo(UPDATED_MONTO_DOLARES);
        assertThat(testCosto.getPorcentaje()).isEqualTo(UPDATED_PORCENTAJE);
        assertThat(testCosto.getIva()).isEqualTo(UPDATED_IVA);
        assertThat(testCosto.getTotalDolares()).isEqualTo(UPDATED_TOTAL_DOLARES);
    }

    @Test
    @Transactional
    public void updateNonExistingCosto() throws Exception {
        int databaseSizeBeforeUpdate = costoRepository.findAll().size();

        // Create the Costo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCostoMockMvc.perform(put("/api/costos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costo)))
            .andExpect(status().isBadRequest());

        // Validate the Costo in the database
        List<Costo> costoList = costoRepository.findAll();
        assertThat(costoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCosto() throws Exception {
        // Initialize the database
        //costoService.save(costo);

        int databaseSizeBeforeDelete = costoRepository.findAll().size();

        // Delete the costo
        restCostoMockMvc.perform(delete("/api/costos/{id}", costo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Costo> costoList = costoRepository.findAll();
        assertThat(costoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Costo.class);
        Costo costo1 = new Costo();
        costo1.setId(1L);
        Costo costo2 = new Costo();
        costo2.setId(costo1.getId());
        assertThat(costo1).isEqualTo(costo2);
        costo2.setId(2L);
        assertThat(costo1).isNotEqualTo(costo2);
        costo1.setId(null);
        assertThat(costo1).isNotEqualTo(costo2);
    }
}
