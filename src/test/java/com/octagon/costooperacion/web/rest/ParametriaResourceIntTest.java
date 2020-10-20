package com.octagon.costooperacion.web.rest;

import com.octagon.costooperacion.CostooperacionApp;

import com.octagon.costooperacion.domain.Parametria;
import com.octagon.costooperacion.repository.ParametriaRepository;
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
import java.util.List;


import static com.octagon.costooperacion.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ParametriaResource REST controller.
 *
 * @see //ParametriaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CostooperacionApp.class)
public class ParametriaResourceIntTest {

    private static final String DEFAULT_DESCRIPCION_LARGA = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION_LARGA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION_CORTA = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION_CORTA = "BBBBBBBBBB";

    private static final String DEFAULT_GRUPO_1 = "AAAAAAAAAA";
    private static final String UPDATED_GRUPO_1 = "BBBBBBBBBB";

    private static final String DEFAULT_GRUPO_2 = "AAAAAAAAAA";
    private static final String UPDATED_GRUPO_2 = "BBBBBBBBBB";

    @Autowired
    private ParametriaRepository parametriaRepository;

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

    private MockMvc restParametriaMockMvc;

    private Parametria parametria;

    @Before
    public void setup() {
        /*MockitoAnnotations.initMocks(this);
        final ParametriaResource parametriaResource = new ParametriaResource(parametriaRepository);
        this.restParametriaMockMvc = MockMvcBuilders.standaloneSetup(parametriaResource)
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
    public static Parametria createEntity(EntityManager em) {
        Parametria parametria = new Parametria()
            .descripcionLarga(DEFAULT_DESCRIPCION_LARGA)
            .descripcionCorta(DEFAULT_DESCRIPCION_CORTA)
            .grupo1(DEFAULT_GRUPO_1)
            .grupo2(DEFAULT_GRUPO_2);
        return parametria;
    }

    @Before
    public void initTest() {
        parametria = createEntity(em);
    }

    @Test
    @Transactional
    public void createParametria() throws Exception {
        int databaseSizeBeforeCreate = parametriaRepository.findAll().size();

        // Create the Parametria
        restParametriaMockMvc.perform(post("/api/parametrias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametria)))
            .andExpect(status().isCreated());

        // Validate the Parametria in the database
        List<Parametria> parametriaList = parametriaRepository.findAll();
        assertThat(parametriaList).hasSize(databaseSizeBeforeCreate + 1);
        Parametria testParametria = parametriaList.get(parametriaList.size() - 1);
        assertThat(testParametria.getDescripcionLarga()).isEqualTo(DEFAULT_DESCRIPCION_LARGA);
        assertThat(testParametria.getDescripcionCorta()).isEqualTo(DEFAULT_DESCRIPCION_CORTA);
        assertThat(testParametria.getGrupo1()).isEqualTo(DEFAULT_GRUPO_1);
        assertThat(testParametria.getGrupo2()).isEqualTo(DEFAULT_GRUPO_2);
    }

    @Test
    @Transactional
    public void createParametriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parametriaRepository.findAll().size();

        // Create the Parametria with an existing ID
        //parametria.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParametriaMockMvc.perform(post("/api/parametrias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametria)))
            .andExpect(status().isBadRequest());

        // Validate the Parametria in the database
        List<Parametria> parametriaList = parametriaRepository.findAll();
        assertThat(parametriaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllParametrias() throws Exception {
        // Initialize the database
        parametriaRepository.saveAndFlush(parametria);

        // Get all the parametriaList
        restParametriaMockMvc.perform(get("/api/parametrias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        //.andExpect(jsonPath("$.[*].id").value(hasItem(parametria.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcionLarga").value(hasItem(DEFAULT_DESCRIPCION_LARGA.toString())))
            .andExpect(jsonPath("$.[*].descripcionCorta").value(hasItem(DEFAULT_DESCRIPCION_CORTA.toString())))
            .andExpect(jsonPath("$.[*].grupo1").value(hasItem(DEFAULT_GRUPO_1.toString())))
            .andExpect(jsonPath("$.[*].grupo2").value(hasItem(DEFAULT_GRUPO_2.toString())));
    }
    
    @Test
    @Transactional
    public void getParametria() throws Exception {
        // Initialize the database
        parametriaRepository.saveAndFlush(parametria);

        // Get the parametria
        restParametriaMockMvc.perform(get("/api/parametrias/{id}", parametria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
          //  .andExpect(jsonPath("$.id").value(parametria.getId().intValue()))
            .andExpect(jsonPath("$.descripcionLarga").value(DEFAULT_DESCRIPCION_LARGA.toString()))
            .andExpect(jsonPath("$.descripcionCorta").value(DEFAULT_DESCRIPCION_CORTA.toString()))
            .andExpect(jsonPath("$.grupo1").value(DEFAULT_GRUPO_1.toString()))
            .andExpect(jsonPath("$.grupo2").value(DEFAULT_GRUPO_2.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParametria() throws Exception {
        // Get the parametria
        restParametriaMockMvc.perform(get("/api/parametrias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParametria() throws Exception {
        // Initialize the database
        parametriaRepository.saveAndFlush(parametria);

        int databaseSizeBeforeUpdate = parametriaRepository.findAll().size();

        // Update the parametria
        Parametria updatedParametria = parametriaRepository.findById(1L).get();
        // Disconnect from session so that the updates on updatedParametria are not directly saved in db
        em.detach(updatedParametria);
        updatedParametria
            .descripcionLarga(UPDATED_DESCRIPCION_LARGA)
            .descripcionCorta(UPDATED_DESCRIPCION_CORTA)
            .grupo1(UPDATED_GRUPO_1)
            .grupo2(UPDATED_GRUPO_2);

        restParametriaMockMvc.perform(put("/api/parametrias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParametria)))
            .andExpect(status().isOk());

        // Validate the Parametria in the database
        List<Parametria> parametriaList = parametriaRepository.findAll();
        assertThat(parametriaList).hasSize(databaseSizeBeforeUpdate);
        Parametria testParametria = parametriaList.get(parametriaList.size() - 1);
        assertThat(testParametria.getDescripcionLarga()).isEqualTo(UPDATED_DESCRIPCION_LARGA);
        assertThat(testParametria.getDescripcionCorta()).isEqualTo(UPDATED_DESCRIPCION_CORTA);
        assertThat(testParametria.getGrupo1()).isEqualTo(UPDATED_GRUPO_1);
        assertThat(testParametria.getGrupo2()).isEqualTo(UPDATED_GRUPO_2);
    }

    @Test
    @Transactional
    public void updateNonExistingParametria() throws Exception {
        int databaseSizeBeforeUpdate = parametriaRepository.findAll().size();

        // Create the Parametria

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParametriaMockMvc.perform(put("/api/parametrias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametria)))
            .andExpect(status().isBadRequest());

        // Validate the Parametria in the database
        List<Parametria> parametriaList = parametriaRepository.findAll();
        assertThat(parametriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParametria() throws Exception {
        // Initialize the database
        parametriaRepository.saveAndFlush(parametria);

        int databaseSizeBeforeDelete = parametriaRepository.findAll().size();

        // Delete the parametria
        restParametriaMockMvc.perform(delete("/api/parametrias/{id}", parametria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Parametria> parametriaList = parametriaRepository.findAll();
        assertThat(parametriaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parametria.class);
        Parametria parametria1 = new Parametria();
        //parametria1.setId(1L);
        Parametria parametria2 = new Parametria();
        parametria2.setId(parametria1.getId());
        assertThat(parametria1).isEqualTo(parametria2);
        //parametria2.setId(2L);
        assertThat(parametria1).isNotEqualTo(parametria2);
        parametria1.setId(null);
        assertThat(parametria1).isNotEqualTo(parametria2);
    }
}
