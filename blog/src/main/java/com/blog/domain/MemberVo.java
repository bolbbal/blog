package com.blog.domain;

import lombok.Data;

@Data
public class MemberVo {

	private Long id;
	private String username;
	private String password;
	private String email;
	private String role;
}
