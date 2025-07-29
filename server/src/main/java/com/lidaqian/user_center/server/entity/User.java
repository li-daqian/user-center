package com.lidaqian.user_center.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "user")
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE user SET is_deleted = true, deleted_at = #{T(java.lang.System).currentTimeMillis()} WHERE id = ?")
@SQLRestriction(value = "is_deleted = false")
public class User extends BaseEntity {

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Enumerated(value = jakarta.persistence.EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        NORMAL("NORMAL"),
        LOCKED("LOCKED"),
        DISABLED("DISABLED");

        private final String description;
    }
}
