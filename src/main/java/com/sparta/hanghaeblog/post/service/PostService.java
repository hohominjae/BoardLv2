package com.sparta.hanghaeblog.post.service;

import com.sparta.hanghaeblog.common.code.HanghaeBlogErrorCode;
import com.sparta.hanghaeblog.common.exception.HanghaeBlogException;
import com.sparta.hanghaeblog.post.dto.PostRequestDto;
import com.sparta.hanghaeblog.post.dto.PostResponseDto;
import com.sparta.hanghaeblog.post.entity.PostEntity;
import com.sparta.hanghaeblog.post.repository.PostRepository;
import com.sparta.hanghaeblog.user.entity.UserEntity;
import com.sparta.hanghaeblog.common.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * PostService.
 */
@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final JwtUtil jwtUtil;

  /**
   * Create Post.
   */
  @Transactional
  public PostResponseDto createPost(PostRequestDto requestDto, HttpServletRequest request) {

    // 토큰 체크 추가
    UserEntity userEntity = jwtUtil.checkToken(request);

    if (userEntity == null) {
      throw new HanghaeBlogException(HanghaeBlogErrorCode.NOT_FOUND_USER, null);
    }

    PostEntity postEntity = PostEntity.builder()
        .requestDto(requestDto)
        .userEntity(userEntity)
        .build();

    postRepository.save(postEntity);
    return new PostResponseDto(postEntity);
  }

  /**
   * Get all post.
   */
  @Transactional(readOnly = true) // readOnly true인 경우, JPA 영속성 컨텍스트에 갱신되지 않기 때문에, 조회 시 false로 설정하는 것보다 더 빠르게 조회가 가능함.
  public List<PostResponseDto> getPostList() {
    List<PostEntity> postEntities = postRepository.findAllByOrderByModifiedAtDesc();

    List<PostResponseDto> postResponseDto = new ArrayList<>();

    postEntities.forEach(postEntity -> postResponseDto.add(new PostResponseDto(postEntity)));

    return postResponseDto;
  }

  /**
   * Get post by id.
   */
  @Transactional(readOnly = true)
  public PostResponseDto getPost(Long id) {
    PostEntity postEntity = postRepository.findById(id)
        .orElseThrow(() -> new HanghaeBlogException(HanghaeBlogErrorCode.NOT_FOUND_POST, null));
    return new PostResponseDto(postEntity);
  }

  /**
   * Update post by id.
   */
  @Transactional
  public PostResponseDto updatePost(Long id, PostRequestDto requestDto, HttpServletRequest request) {

    // 토큰 체크 추가
    UserEntity userEntity = jwtUtil.checkToken(request);

    if (userEntity == null) {
      throw new HanghaeBlogException(HanghaeBlogErrorCode.NOT_FOUND_USER, null);
    }

    PostEntity postEntity = postRepository.findById(id).orElseThrow(
        () -> new HanghaeBlogException(HanghaeBlogErrorCode.NOT_FOUND_POST, null)
    );

    if (!postEntity.getUserEntity().equals(userEntity)) {
      throw new HanghaeBlogException(HanghaeBlogErrorCode.UNAUTHORIZED_USER, null);
    }

    postEntity.update(requestDto);
    return new PostResponseDto(postEntity);
  }

  /**
   * Delete post.
   */
  @Transactional
  public void deletePost(Long id, HttpServletRequest request) {

    // 토큰 체크 추가
    UserEntity userEntity = jwtUtil.checkToken(request);

    if (userEntity == null) {
      throw new HanghaeBlogException(HanghaeBlogErrorCode.NOT_FOUND_USER, null);
    }

    PostEntity postEntity = postRepository.findById(id).orElseThrow(
        () -> new HanghaeBlogException(HanghaeBlogErrorCode.NOT_FOUND_POST, null)
    );

    if (postEntity.getUserEntity().equals(userEntity)) {
      postRepository.delete(postEntity);
    }
  }
}