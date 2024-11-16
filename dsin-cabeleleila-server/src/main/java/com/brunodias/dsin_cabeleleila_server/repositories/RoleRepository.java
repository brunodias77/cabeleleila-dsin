package com.brunodias.dsin_cabeleleila_server.repositories;

import com.brunodias.dsin_cabeleleila_server.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);

}
