package com.blog.controller;

import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.domain.CommentVo;
import com.blog.service.CommentService;

@RestController //비동기식 컨트롤러
@RequestMapping("/comment")
public class CommentController {

	@Autowired
    private CommentService commentService;
   
   // 댓글 등록 처리
    @PostMapping("/add")
    public ResponseEntity<CommentVo> addComment(@RequestBody CommentVo commentVo, Principal principal) {
        // 댓글 작성자, 현재 시간 등을 설정 (예시로 "guest"로 설정, 실제 구현에서는 로그인 사용자로 설정)
        commentVo.setCom_writer(principal.getName());  // 실제로는 세션에서 가져온 사용자 정보를 설정
     // 현재 날짜와 시간을 LocalDateTime으로 설정
        commentVo.setCom_regdate(LocalDateTime.now());

        // 서비스 호출하여 댓글 등록
        Long result = commentService.addComment(commentVo);

        // 등록된 댓글 반환 (성공 시)
        if (result > 0) {
            return ResponseEntity.ok(commentVo);
        } else {
            return ResponseEntity.status(400).body(null);  // 등록 실패 시
        }
    }
   
    @DeleteMapping("/delete/{com_bno}")
    public ResponseEntity<String> deleteComment(@PathVariable("com_bno") Long com_bno, Principal principal) {
        // 1. 로그인 여부 확인
        if (principal == null || principal.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        String username = principal.getName();

        // 2. 댓글 조회
        CommentVo cmv = commentService.findById(com_bno);
        if (cmv == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("댓글을 찾을 수 없습니다.");
        }

        // 3. 작성자 검증
        if (!cmv.getCom_writer().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제 권한이 없습니다.");
        }

        // 4. 댓글 삭제
        Long result = commentService.removeComment(com_bno);
        if (result > 0) {
            return ResponseEntity.ok("댓글이 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 삭제 실패");
        }
    }

    
    @PutMapping("/update")
    public ResponseEntity<String> updateComment(@RequestBody CommentVo commentVo) {
        Long result = commentService.updateComment(commentVo);

        if (result > 0) {
            return ResponseEntity.ok("댓글 수정 성공");
        } else {
            return ResponseEntity.status(400).body("댓글 수정 실패");
        }
    }

	
}