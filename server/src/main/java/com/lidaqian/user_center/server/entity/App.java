package com.lidaqian.user_center.server.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Data
@Entity
@Table(name = "app")
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE app SET is_deleted = true, deleted_at = #{T(java.lang.System).currentTimeMillis()} WHERE id = ?")
@SQLRestriction(value = "is_deleted = false")
public class App extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "app_id", nullable = false)
    private String appId;

    @Column(name = "secret", nullable = false)
    private String secret;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "version", nullable = false)
    private String version;

    @Column(name = "index_url", nullable = false)
    private String indexUrl;

    @Enumerated(value = jakarta.persistence.EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "app")
    private List<Role> role;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "app")
    private List<Permission> permission;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        ACTIVE("激活"),
        INACTIVE("未激活"),
        DISABLED("禁用"),
        ;

        private final String description;
    }
}
