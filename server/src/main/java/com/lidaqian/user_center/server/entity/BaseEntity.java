package com.lidaqian.user_center.server.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_time")
    private Date updatedTime;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "deleted_at", nullable = false)
    private Long deletedAt;

    @PrePersist
    protected void prePersist() {
        if (this.id == null) {
            this.id = UuidCreator.getTimeOrderedEpoch();
        }

        if (this.isDeleted == null) {
            this.isDeleted = false;
        }
        if (this.deletedAt == null) {
            this.deletedAt = 0L;
        }

        if (this.createdBy == null) {
            this.createdBy = "";
        }
        if (this.updatedBy == null) {
            this.updatedBy = "";
        }

        Date now = new Date();
        if (this.createdTime == null) {
            this.createdTime = now;
        }
        if (this.updatedTime == null) {
            this.updatedTime = now;
        }
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedTime = new Date();
    }

    @PreRemove
    protected void preRemove() {
        this.updatedTime = new Date();
    }
}
