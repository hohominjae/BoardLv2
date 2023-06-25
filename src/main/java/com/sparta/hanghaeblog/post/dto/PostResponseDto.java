package com.sparta.hanghaeblog.post.dto;

import com.sparta.hanghaeblog.post.entity.PostEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * PostResponseDto.
 */
@Getter
public class PostResponseDto { // 게시물 CRUD 요청에 대한 응답으로 사용되는 DTO
  private long contentId;
  private String title;
  private String contents;
  private String username;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  /**
   * initializer using Builder.
   */
  @Builder
  public PostResponseDto(PostEntity postEntity) {
    this.contentId = postEntity.getPostId();
    this.title = postEntity.getTitle();
    this.contents = postEntity.getContent();
    this.username = postEntity.getUserEntity().getUsername();
    this.createdAt = postEntity.getCreatedAt();
    this.modifiedAt = postEntity.getModifiedAt();
  }
}