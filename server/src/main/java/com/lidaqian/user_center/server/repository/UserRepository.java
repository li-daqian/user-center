package com.lidaqian.user_center.server.repository;

import com.lidaqian.user_center.server.entity.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface UserRepository extends JpaRepositoryImplementation<User, UUID> {

}
