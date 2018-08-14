package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.UserTypeService;
import io.github.jhipster.application.domain.UserType;
import io.github.jhipster.application.repository.UserTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing UserType.
 */
@Service
@Transactional
public class UserTypeServiceImpl implements UserTypeService {

    private final Logger log = LoggerFactory.getLogger(UserTypeServiceImpl.class);

    private final UserTypeRepository userTypeRepository;

    public UserTypeServiceImpl(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    /**
     * Save a userType.
     *
     * @param userType the entity to save
     * @return the persisted entity
     */
    @Override
    public UserType save(UserType userType) {
        log.debug("Request to save UserType : {}", userType);        return userTypeRepository.save(userType);
    }

    /**
     * Get all the userTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserType> findAll(Pageable pageable) {
        log.debug("Request to get all UserTypes");
        return userTypeRepository.findAll(pageable);
    }


    /**
     * Get one userType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserType> findOne(Long id) {
        log.debug("Request to get UserType : {}", id);
        return userTypeRepository.findById(id);
    }

    /**
     * Delete the userType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserType : {}", id);
        userTypeRepository.deleteById(id);
    }
}
