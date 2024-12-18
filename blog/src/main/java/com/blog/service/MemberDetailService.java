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
// ?ƒ?„±?ë¥? ?†µ?•´ ê°ì²´ ?ƒ?„±?•  ?ˆ˜ ?ˆì§?ë§? ê°ì²´ë¥? ?ƒ?„±?•  ?ˆ˜ ?ˆ?Š” ë¹Œë”ë¥? build() ?•¨?ˆ˜ë¥?
// ?†µ?•´ ?–»ê³?,
// ?•´?‹¹ ê°ì²´?— ?„¸?Œ…?•˜ê³ ì ?•˜?Š” ê°’ì„ ?„¸?Œ…?•œ ?›„ ë§ˆì?ë§‰ìœ¼ë¡? build(). ë¥? ?†µ?•´ ë¹Œë”
// ?‘?™?‹œì¼? ê°ì²´ë¥? ?ƒ?„±
public class MemberDetailService implements UserDetailsService {
	// UserDetailsService
	// DB?—?„œ ?šŒ?› ? •ë³´ë?? ê°?? ¸?˜¤?Š” ?—­?• ?„ ?•˜?Š” ?¸?„°?˜?´?Š¤

	private final MemberMapper mapper;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// UserDetails
		// ?šŒ?› ? •ë³´ë?? ?‹´ê¸°ìœ„?•´ ?‚¬?š©?•˜?Š” ?¸?„°?˜?´?Š¤
		MemberVo member = mapper.loginCheck(username);

		String encodePassword = (member == null) ? "" : member.getPassword();

		if (member == null) {
			throw new UsernameNotFoundException(username);
		}
		return User.builder().username(member.getUsername())
				.password(encodePassword).authorities(member.getRole()).build();
		// User Userdetails ?¸?„°?˜?´?Š¤ êµ¬í˜„
	}

}
