package com.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.domain.BoardAttachVo;
import com.blog.domain.BoardVo;
import com.blog.mapper.BoardAttachMapper;
import com.blog.mapper.BoardMapper;
import com.blog.util.Criteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardMapper mapper;
	private final BoardAttachMapper attachMapper;

	@Transactional
	public void register(BoardVo board) {

		mapper.insertSelectKey(board);

		if (board.getAttachList() == null
				|| board.getAttachList().size() <= 0) {
			return;
		} else {
			board.getAttachList().forEach(attach -> {
				attach.setBno(board.getBno());
				attachMapper.insert(attach);
			});
		}

	}

	public List<BoardVo> getList(Criteria cri) {

		List<BoardVo> list = mapper.getList(cri);

		for (BoardVo board : list) {
			List<BoardAttachVo> attachList = attachMapper
					.findByBno(board.getBno());

			board.setAttachList(attachList);
		}

		// for (BoardVo board : list) {
		// List<ReplyVo> commentList = commentMapper
		// .getComment(board.getBno());
		//
		// board.setCommentList(commentList);
		// }

		return list;
	}

	public List<BoardVo> getListPaging(Criteria cri) {
		List<BoardVo> list = mapper.getList(cri);

		for (BoardVo board : list) {
			List<BoardAttachVo> attachList = attachMapper
					.findByBno(board.getBno());

			board.setAttachList(attachList);
		}

		// for (BoardVo board : list) {
		// List<ReplyVo> commentList = commentMapper
		// .getComment(board.getBno());
		//
		// board.setCommentList(commentList);
		// }

		return list;
	}

	public int getBoardCount() {
		return mapper.getBoardCount();
	}

	public int getBoardCountPaging(Criteria cri) {
		return mapper.getBoardCountPaging(cri);
	}

	public BoardVo getDetail(Long bno) {
		List<BoardAttachVo> list = attachMapper.findByBno(bno);
		// List<ReplyVo> comment = commentMapper.getComment(bno);

		BoardVo board = mapper.getDetail(bno);

		board.setAttachList(list);
		// board.setCommentList(comment);

		return board;
	}

	public BoardVo getNext(Long bno) {
		return mapper.getNext(bno);
	}

	public BoardVo getPrev(Long bno) {
		return mapper.getPrev(bno);
	}

	public void updateBoard(BoardVo board) {

		boolean result = mapper.updateBoard(board) >= 1;

		if (result && !(board.getAttachList() == null)) {

			attachMapper.deleteAll(board.getBno());

			board.getAttachList().forEach(attach -> {
				attach.setBno(board.getBno());
				attachMapper.insert(attach);
			});
		}
	}

	public boolean deleteBoard(Long bno) {

		boolean result = false;

		if (mapper.deleteBoard(bno) > 0) {
			result = true;
		}

		return result;
	}

	public List<BoardAttachVo> findByBno(Long bno) {
		return attachMapper.findByBno(bno);
	}

	// public void insertComment(ReplyVo comment) {
	// commentMapper.insertComment(comment);
	// }
	//
	// public int countComment(Long board_bno) {
	// return commentMapper.CommentCount(board_bno);
	// }
	//
	// public void updateComment(ReplyVo comment) {
	// commentMapper.commentModify(comment);
	// }
	//
	// public void deleteComment(Long reply_bno) {
	// commentMapper.commentDelete(reply_bno);
	// }

}
