package com.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.blog.domain.BoardVo;
import com.blog.util.Criteria;

@Mapper
public interface BoardMapper {

	public Integer insertSelectKey(BoardVo board);

	public List<BoardVo> getList(Criteria cri);

	public List<BoardVo> getListPaging(Criteria cri);

	public int getBoardCount();

	public int getBoardCountPaging(Criteria cri);

	public BoardVo getDetail(Long bno);

	public BoardVo getNext(Long bno);

	public BoardVo getPrev(Long bno);

	public Integer updateBoard(BoardVo board);

	public Integer deleteBoard(Long bno);

}
