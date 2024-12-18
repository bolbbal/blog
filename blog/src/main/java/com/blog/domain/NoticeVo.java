package com.blog.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NoticeVo {
	private Long idx;
	private String title;
	private String content;
	private String writer;
	private LocalDateTime regdate;
	private LocalDateTime modifydate;
}
