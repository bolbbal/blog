package com.blog.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) //security 6.x ?´?ƒ
public class SecurityConfig {

	// ë¹„ë?ë²ˆí˜¸ ?•”?˜¸?™”
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// ?‚¬?š©? ê³„ì • ?ƒ?„±
//	@Bean
//	public UserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder) {
//		UserDetails user = User.withUsername("admin") // ?‚¬?š©? ?´ë¦?
//				.password(passwordEncoder.encode("password")) // ë¹„ë?ë²ˆí˜¸ ?•”?˜¸?™”
//				.roles("USER") // ?‚¬?š©? ê¶Œí•œ
//				.build();
//
//		return new InMemoryUserDetailsManager(user); // ë©”ëª¨ë¦¬ì—?„œ ê´?ë¦?
//	}

	// ì±? 150 ?˜?´ì§??— ?ˆê¸? ?•œ?° ?˜‘ê°™ì´?Š” ?„´?„´ (???”±ë²„ì „?„)
	// ?¸ì¦?, ?¸ê°?ë¥? ì²˜ë¦¬?•¨
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//ì±? 169?˜?´ì§??—?„œ ë¡œê·¸?¸
		// 3.x ë²„ì „ ?´?ƒ?? lamda ?‹?œ¼ë¡? ?‘œ?˜„?•´?•¼?•¨
		http
		.formLogin((formlogin) -> formlogin
				.loginPage("/mem/login") //ë¡œê·¸?¸ ?˜?´ì§? url ?š”ì²? ì£¼ì†Œ
				.usernameParameter("username") //ë¡œê·¸?¸ ?‹œ ?‚¬?š©?•  ?ŒŒ?¼ë¯¸í„° ?´ë¦? ?„¤? •
				.defaultSuccessUrl("/") //ë¡œê·¸?¸ ?„±ê³? ?‹œ redirect
				.failureUrl("/mem/login") //ë¡œê·¸?¸ ?‹¤?Œ¨ ?‹œ redirect
				)
		.logout((logout) -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/mem/logout"))
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)
				)
		.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/error").permitAll()
				.requestMatchers("/index").permitAll()
				.requestMatchers("/", "/mem/**", "/port/**").permitAll()
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll() //?´ ?œ„?— ?„¤? •?˜ì§? ?•Š?? ?š”ì²??? ì°¨ë‹¨
				)
		.exceptionHandling(handling -> handling
				.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
				)
		;
		return http.build();
	}

	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
//		StrictHttpFirewall firewall = new StrictHttpFirewall();
//		firewall.setAllowUrlEncodedSlash(true); // ?¸ì½”ë”©?œ ?Š¬?˜?‹œ ?—ˆ?š©
//		firewall.setAllowSemicolon(true); // ?„¸ë¯¸ì½œë¡? ?—ˆ?š©
//		firewall.setAllowUrlEncodedPercent(true); // ?¼?„¼?Š¸ ?¸ì½”ë”© ?—ˆ?š©
//		firewall.setAllowUrlEncodedDoubleSlash(true);
		return new DefaultHttpFirewall();
	}

	
}
