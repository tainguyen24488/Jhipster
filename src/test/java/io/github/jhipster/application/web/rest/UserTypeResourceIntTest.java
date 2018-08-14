package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.UserType;
import io.github.jhipster.application.domain.User;
import io.github.jhipster.application.repository.UserTypeRepository;
import io.github.jhipster.application.service.UserTypeService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserTypeResource REST controller.
 *
 * @see UserTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class UserTypeResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private UserTypeRepository userTypeRepository;

    

    @Autowired
    private UserTypeService userTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserTypeMockMvc;

    private UserType userType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserTypeResource userTypeResource = new UserTypeResource(userTypeService);
        this.restUserTypeMockMvc = MockMvcBuilders.standaloneSetup(userTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserType createEntity(EntityManager em) {
        UserType userType = new UserType()
            .type(DEFAULT_TYPE);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        userType.setUsertype(user);
        return userType;
    }

    @Before
    public void initTest() {
        userType = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserType() throws Exception {
        int databaseSizeBeforeCreate = userTypeRepository.findAll().size();

        // Create the UserType
        restUserTypeMockMvc.perform(post("/api/user-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userType)))
            .andExpect(status().isCreated());

        // Validate the UserType in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeCreate + 1);
        UserType testUserType = userTypeList.get(userTypeList.size() - 1);
        assertThat(testUserType.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createUserTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userTypeRepository.findAll().size();

        // Create the UserType with an existing ID
        userType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserTypeMockMvc.perform(post("/api/user-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userType)))
            .andExpect(status().isBadRequest());

        // Validate the UserType in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userTypeRepository.findAll().size();
        // set the field null
        userType.setType(null);

        // Create the UserType, which fails.

        restUserTypeMockMvc.perform(post("/api/user-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userType)))
            .andExpect(status().isBadRequest());

        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserTypes() throws Exception {
        // Initialize the database
        userTypeRepository.saveAndFlush(userType);

        // Get all the userTypeList
        restUserTypeMockMvc.perform(get("/api/user-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    

    @Test
    @Transactional
    public void getUserType() throws Exception {
        // Initialize the database
        userTypeRepository.saveAndFlush(userType);

        // Get the userType
        restUserTypeMockMvc.perform(get("/api/user-types/{id}", userType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingUserType() throws Exception {
        // Get the userType
        restUserTypeMockMvc.perform(get("/api/user-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserType() throws Exception {
        // Initialize the database
        userTypeService.save(userType);

        int databaseSizeBeforeUpdate = userTypeRepository.findAll().size();

        // Update the userType
        UserType updatedUserType = userTypeRepository.findById(userType.getId()).get();
        // Disconnect from session so that the updates on updatedUserType are not directly saved in db
        em.detach(updatedUserType);
        updatedUserType
            .type(UPDATED_TYPE);

        restUserTypeMockMvc.perform(put("/api/user-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserType)))
            .andExpect(status().isOk());

        // Validate the UserType in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeUpdate);
        UserType testUserType = userTypeList.get(userTypeList.size() - 1);
        assertThat(testUserType.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserType() throws Exception {
        int databaseSizeBeforeUpdate = userTypeRepository.findAll().size();

        // Create the UserType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserTypeMockMvc.perform(put("/api/user-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userType)))
            .andExpect(status().isBadRequest());

        // Validate the UserType in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserType() throws Exception {
        // Initialize the database
        userTypeService.save(userType);

        int databaseSizeBeforeDelete = userTypeRepository.findAll().size();

        // Get the userType
        restUserTypeMockMvc.perform(delete("/api/user-types/{id}", userType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserType.class);
        UserType userType1 = new UserType();
        userType1.setId(1L);
        UserType userType2 = new UserType();
        userType2.setId(userType1.getId());
        assertThat(userType1).isEqualTo(userType2);
        userType2.setId(2L);
        assertThat(userType1).isNotEqualTo(userType2);
        userType1.setId(null);
        assertThat(userType1).isNotEqualTo(userType2);
    }
}
