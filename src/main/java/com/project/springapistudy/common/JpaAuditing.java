package com.project.springapistudy.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class JpaAuditing {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @CreatedBy
    @Column(updatable = false)
    private String createId;

    @LastModifiedDate
    private LocalDateTime updateDate;

    @LastModifiedBy
    private String updateId;
}
