package com.example.gsmoa.entity;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
    // 글 생성 시간
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdTime;

    // 글 업데이트 시간
    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedTime;
}