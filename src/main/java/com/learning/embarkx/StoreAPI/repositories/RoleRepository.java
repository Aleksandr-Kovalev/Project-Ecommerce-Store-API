package com.learning.embarkx.StoreAPI.repositories;

import com.learning.embarkx.StoreAPI.model.AppRole;
import com.learning.embarkx.StoreAPI.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
