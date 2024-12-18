package com.blog.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.domain.MemberVo;
import com.blog.mapper.MemberMapper;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
@Builder
// ?��?��?���? ?��?�� 객체 ?��?��?�� ?�� ?���?�? 객체�? ?��?��?�� ?�� ?��?�� 빌더�? build() ?��?���?
// ?��?�� ?���?,
// ?��?�� 객체?�� ?��?��?��고자 ?��?�� 값을 ?��?��?�� ?�� 마�?막으�? build(). �? ?��?�� 빌더
// ?��?��?���? 객체�? ?��?��
public class MemberDetailService implements UserDetailsService {
	// UserDetailsService
	// DB?��?�� ?��?�� ?��보�?? �??��?��?�� ?��?��?�� ?��?�� ?��?��?��?��?��

	private final MemberMapper mapper;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// UserDetails
		// ?��?�� ?��보�?? ?��기위?�� ?��?��?��?�� ?��?��?��?��?��
		MemberVo member = mapper.loginCheck(username);

		String encodePassword = (member == null) ? "" : member.getPassword();

		if (member == null) {
			throw new UsernameNotFoundException(username);
		}
		return User.builder().username(member.getUsername())
				.password(encodePassword).authorities(member.getRole()).build();
		// User Userdetails ?��?��?��?��?�� 구현
	}

}
