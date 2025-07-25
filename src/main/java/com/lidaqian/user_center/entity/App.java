package com.lidaqian.user_center.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "app")
@SQLDelete(sql = "UPDATE `app` SET is_deleted = true, deleted_at = ROUND(UNIX_TIMESTAMP(NOW(4))*1000) WHERE id = ?")
@SQLRestriction(value = "is_deleted = false")
public class App extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;
}
