package com.blog.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class BoardVo {

	private long bno;
	private String title;
	private String content;
	private String writer;
	private LocalDateTime regdate;
	private LocalDateTime updatedate;

	private List<BoardAttachVo> attachList;
}
