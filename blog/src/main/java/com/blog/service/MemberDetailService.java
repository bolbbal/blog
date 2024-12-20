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
// ??±?λ₯? ?΅?΄ κ°μ²΄ ??±?  ? ?μ§?λ§? κ°μ²΄λ₯? ??±?  ? ?? λΉλλ₯? build() ?¨?λ₯?
// ?΅?΄ ?»κ³?,
// ?΄?Ή κ°μ²΄? ?Έ??κ³ μ ?? κ°μ ?Έ?? ? λ§μ?λ§μΌλ‘? build(). λ₯? ?΅?΄ λΉλ
// ???μΌ? κ°μ²΄λ₯? ??±
public class MemberDetailService implements UserDetailsService {
	// UserDetailsService
	// DB?? ?? ? λ³΄λ?? κ°?? Έ?€? ?­? ? ?? ?Έ?°??΄?€

	private final MemberMapper mapper;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// UserDetails
		// ?? ? λ³΄λ?? ?΄κΈ°μ?΄ ?¬?©?? ?Έ?°??΄?€
		MemberVo member = mapper.loginCheck(username);

		String encodePassword = (member == null) ? "" : member.getPassword();

		if (member == null) {
			throw new UsernameNotFoundException(username);
		}
		return User.builder().username(member.getUsername())
				.password(encodePassword).authorities(member.getRole()).build();
		// User Userdetails ?Έ?°??΄?€ κ΅¬ν
	}

}
