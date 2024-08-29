package com.techon.login.repository;

import com.techon.login.entity.Role;
import com.techon.login.entity.Token;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
