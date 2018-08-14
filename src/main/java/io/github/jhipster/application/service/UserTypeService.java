package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.UserType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing UserType.
 */
public interface UserTypeService {

    /**
     * Save a userType.
     *
     * @param userType the entity to save
     * @return the persisted entity
     */
    UserType save(UserType userType);

    /**
     * Get all the userTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserType> findAll(Pageable pageable);


    /**
     * Get the "id" userType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserType> findOne(Long id);

    /**
     * Delete the "id" userType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
