package com.lidaqian.user_center.server.repository;

import com.lidaqian.user_center.server.entity.Permission;
import java.util.UUID;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface PermissionRepository extends JpaRepositoryImplementation<Permission, UUID> {

}
