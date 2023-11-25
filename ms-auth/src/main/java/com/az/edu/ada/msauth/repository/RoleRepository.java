package com.az.edu.ada.msauth.repository;
import az.edu.ada.sdpbackapi.models.ERole;
import az.edu.ada.sdpbackapi.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
