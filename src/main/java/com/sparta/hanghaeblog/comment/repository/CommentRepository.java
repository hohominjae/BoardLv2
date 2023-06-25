package com.sparta.hanghaeblog.comment.repository;

import com.sparta.hanghaeblog.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CommentRepository.
 */
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
