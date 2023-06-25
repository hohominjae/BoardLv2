package com.sparta.hanghaeblog.comment.entity;

import com.sparta.hanghaeblog.common.entity.Timestamped;
import com.sparta.hanghaeblog.post.entity.PostEntity;
import com.sparta.hanghaeblog.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_comment")
public class CommentEntity extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long commentId;

  @Column(name = "content", nullable = false)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "post_id", referencedColumnName = "post_id")
  private PostEntity postEntity;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "username", referencedColumnName = "username")
  private UserEntity userEntity;
}
