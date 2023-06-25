package com.sparta.hanghaeblog.common.entity;

import java.time.LocalDateTime;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Timestamped.
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Timestamped {

  @CreatedDate
  private LocalDateTime createdAt; // 작성 시간

  @LastModifiedDate
  private LocalDateTime modifiedAt; // 수정된 시간
}