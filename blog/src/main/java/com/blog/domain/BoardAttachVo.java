package com.blog.domain;

import lombok.Data;

@Data
public class BoardAttachVo {

	private String uuid;
	private String uploadpath;
	private String filename;
	private String ceoImg;
	private String uploadfile;
	private char filetype;

	private long bno;
}
