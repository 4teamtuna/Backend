package com.example.gsmoa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// 생성 시간과 업데이트 시간을 자동으로 관리하는 BaseEntity 클래스
// @MappedSuperclass 어노테이션을 사용하여 해당 클래스를 상속받는 클래스에서 해당 필드를 사용할 수 있습니다.
// @EntityListeners 어노테이션을 사용하여 해당 클래스에 AuditingEntityListener를 등록합니다.
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