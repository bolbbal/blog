package com.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blog.domain.BoardAttachVo;
import com.blog.domain.BoardVo;
import com.blog.mapper.MainMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainService {

	private final MainMapper mapper;

	public List<BoardVo> getMain() {

		List<BoardVo> list = mapper.getList();

		for (BoardVo board : list) {
			Long bno = board.getBno();

			List<BoardAttachVo> attachList = mapper.findByBno(bno);

			board.setAttachList(attachList);
		}

		return list;
	}
}
