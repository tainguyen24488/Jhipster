package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.UserType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the UserType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {

    @Query("select user_type from UserType user_type where user_type.usertype.login = ?#{principal.username}")
    List<UserType> findByUsertypeIsCurrentUser();

}
