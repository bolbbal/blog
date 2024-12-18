package com.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.domain.CommentVo;
import com.blog.mapper.CommentMapper;

@Service
public class CommentService {

	@Autowired
	private CommentMapper commentMapper;

	// 댓글 등록
	public Long addComment(CommentVo commentVo) {
		return commentMapper.insertComment(commentVo);
	}

	public CommentVo findById(Long com_bno) {
		return commentMapper.getCommentByComBno(com_bno);
	}

	// 댓글 목록 조회
	public List<CommentVo> getComments(Long bno) {
		return commentMapper.getCommentsByBoardNo(bno);
	}

	// 댓글 개수 조회
	public Long getCommentCount(Long bno) {
		return commentMapper.getCommentCountByBoardNo(bno);
	}

	// 댓글 삭제
	public Long removeComment(Long com_bno) {
		return commentMapper.deleteComment(com_bno);
	}

	// 댓글 수정
	public Long updateComment(CommentVo commentVo) {
		return commentMapper.updateComment(commentVo);
	}
}
