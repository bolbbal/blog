package com.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.blog.domain.BoardAttachVo;
import com.blog.domain.BoardVo;

@Mapper
public interface MainMapper {

	public List<BoardVo> getList();

	public List<BoardAttachVo> findByBno(Long bno);

}
