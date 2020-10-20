package com.octagon.costooperacion.web.rest;

import com.octagon.costooperacion.CostooperacionApp;

import com.octagon.costooperacion.domain.MakerAndChecker;
import com.octagon.costooperacion.repository.MakerAndCheckerRepository;
import com.octagon.costooperacion.service.MakerAndCheckerService;
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
 * Test class for the MakerAndCheckerResource REST controller.
 *
 * @see //MakerAndCheckerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CostooperacionApp.class)
public class MakerAndCheckerResourceIntTest {

    private static final Long DEFAULT_USUARIO_ID = 1L;
    private static final Long UPDATED_USUARIO_ID = 2L;

    private static final Long DEFAULT_USUARIO_SUPERVISOR_ID = 1L;
    private static final Long UPDATED_USUARIO_SUPERVISOR_ID = 2L;

    @Autowired
    private MakerAndCheckerRepository makerAndCheckerRepository;

    @Autowired
    private MakerAndCheckerService makerAndCheckerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    //private Validator validator;

    private MockMvc restMakerAndCheckerMockMvc;

    private MakerAndChecker makerAndChecker;

    @Before
    public void setup() {
        /*MockitoAnnotations.initMocks(this);
        final MakerAndCheckerResource makerAndCheckerResource = new MakerAndCheckerResource(makerAndCheckerService);
        this.restMakerAndCheckerMockMvc = MockMvcBuilders.standaloneSetup(makerAndCheckerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();*/
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MakerAndChecker createEntity(EntityManager em) {
        MakerAndChecker makerAndChecker = new MakerAndChecker()
            .usuarioId(DEFAULT_USUARIO_ID)
            .usuarioSupervisorId(DEFAULT_USUARIO_SUPERVISOR_ID);
        return makerAndChecker;
    }

    @Before
    public void initTest() {
        makerAndChecker = createEntity(em);
    }

    @Test
    @Transactional
    public void createMakerAndChecker() throws Exception {
        int databaseSizeBeforeCreate = makerAndCheckerRepository.findAll().size();

        // Create the MakerAndChecker
        restMakerAndCheckerMockMvc.perform(post("/api/maker-and-checkers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(makerAndChecker)))
            .andExpect(status().isCreated());

        // Validate the MakerAndChecker in the database
        List<MakerAndChecker> makerAndCheckerList = makerAndCheckerRepository.findAll();
        assertThat(makerAndCheckerList).hasSize(databaseSizeBeforeCreate + 1);
        MakerAndChecker testMakerAndChecker = makerAndCheckerList.get(makerAndCheckerList.size() - 1);
        assertThat(testMakerAndChecker.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testMakerAndChecker.getUsuarioSupervisorId()).isEqualTo(DEFAULT_USUARIO_SUPERVISOR_ID);
    }

    @Test
    @Transactional
    public void createMakerAndCheckerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = makerAndCheckerRepository.findAll().size();

        // Create the MakerAndChecker with an existing ID
        makerAndChecker.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMakerAndCheckerMockMvc.perform(post("/api/maker-and-checkers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(makerAndChecker)))
            .andExpect(status().isBadRequest());

        // Validate the MakerAndChecker in the database
        List<MakerAndChecker> makerAndCheckerList = makerAndCheckerRepository.findAll();
        assertThat(makerAndCheckerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMakerAndCheckers() throws Exception {
        // Initialize the database
        makerAndCheckerRepository.saveAndFlush(makerAndChecker);

        // Get all the makerAndCheckerList
        restMakerAndCheckerMockMvc.perform(get("/api/maker-and-checkers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(makerAndChecker.getId().intValue())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID.intValue())))
            .andExpect(jsonPath("$.[*].usuarioSupervisorId").value(hasItem(DEFAULT_USUARIO_SUPERVISOR_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getMakerAndChecker() throws Exception {
        // Initialize the database
        makerAndCheckerRepository.saveAndFlush(makerAndChecker);

        // Get the makerAndChecker
        restMakerAndCheckerMockMvc.perform(get("/api/maker-and-checkers/{id}", makerAndChecker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(makerAndChecker.getId().intValue()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID.intValue()))
            .andExpect(jsonPath("$.usuarioSupervisorId").value(DEFAULT_USUARIO_SUPERVISOR_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMakerAndChecker() throws Exception {
        // Get the makerAndChecker
        restMakerAndCheckerMockMvc.perform(get("/api/maker-and-checkers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMakerAndChecker() throws Exception {
        // Initialize the database
        //makerAndCheckerService.save(makerAndChecker);

        int databaseSizeBeforeUpdate = makerAndCheckerRepository.findAll().size();

        // Update the makerAndChecker
        MakerAndChecker updatedMakerAndChecker = makerAndCheckerRepository.findById(makerAndChecker.getId()).get();
        // Disconnect from session so that the updates on updatedMakerAndChecker are not directly saved in db
        em.detach(updatedMakerAndChecker);
        updatedMakerAndChecker
            .usuarioId(UPDATED_USUARIO_ID)
            .usuarioSupervisorId(UPDATED_USUARIO_SUPERVISOR_ID);

        restMakerAndCheckerMockMvc.perform(put("/api/maker-and-checkers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMakerAndChecker)))
            .andExpect(status().isOk());

        // Validate the MakerAndChecker in the database
        List<MakerAndChecker> makerAndCheckerList = makerAndCheckerRepository.findAll();
        assertThat(makerAndCheckerList).hasSize(databaseSizeBeforeUpdate);
        MakerAndChecker testMakerAndChecker = makerAndCheckerList.get(makerAndCheckerList.size() - 1);
        assertThat(testMakerAndChecker.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testMakerAndChecker.getUsuarioSupervisorId()).isEqualTo(UPDATED_USUARIO_SUPERVISOR_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingMakerAndChecker() throws Exception {
        int databaseSizeBeforeUpdate = makerAndCheckerRepository.findAll().size();

        // Create the MakerAndChecker

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMakerAndCheckerMockMvc.perform(put("/api/maker-and-checkers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(makerAndChecker)))
            .andExpect(status().isBadRequest());

        // Validate the MakerAndChecker in the database
        List<MakerAndChecker> makerAndCheckerList = makerAndCheckerRepository.findAll();
        assertThat(makerAndCheckerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMakerAndChecker() throws Exception {
        // Initialize the database
        //makerAndCheckerService.save(makerAndChecker);

        int databaseSizeBeforeDelete = makerAndCheckerRepository.findAll().size();

        // Delete the makerAndChecker
        restMakerAndCheckerMockMvc.perform(delete("/api/maker-and-checkers/{id}", makerAndChecker.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MakerAndChecker> makerAndCheckerList = makerAndCheckerRepository.findAll();
        assertThat(makerAndCheckerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MakerAndChecker.class);
        MakerAndChecker makerAndChecker1 = new MakerAndChecker();
        makerAndChecker1.setId(1L);
        MakerAndChecker makerAndChecker2 = new MakerAndChecker();
        makerAndChecker2.setId(makerAndChecker1.getId());
        assertThat(makerAndChecker1).isEqualTo(makerAndChecker2);
        makerAndChecker2.setId(2L);
        assertThat(makerAndChecker1).isNotEqualTo(makerAndChecker2);
        makerAndChecker1.setId(null);
        assertThat(makerAndChecker1).isNotEqualTo(makerAndChecker2);
    }
}
