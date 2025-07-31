package com.lidaqian.user_center.server.repository;

import com.lidaqian.user_center.server.entity.Role;
import java.util.UUID;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface RoleRepository extends JpaRepositoryImplementation<Role, UUID> {

}
