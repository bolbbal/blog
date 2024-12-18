package com.blog.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {

	private long com_bno;
	private String com_writer;
	private String com_content;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime com_regdate;
	private long bno;

}
