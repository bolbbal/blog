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
@EnableMethodSecurity(prePostEnabled = true) //security 6.x ?��?��
public class SecurityConfig {

	// 비�?번호 ?��?��?��
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// ?��?��?�� 계정 ?��?��
//	@Bean
//	public UserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder) {
//		UserDetails user = User.withUsername("admin") // ?��?��?�� ?���?
//				.password(passwordEncoder.encode("password")) // 비�?번호 ?��?��?��
//				.roles("USER") // ?��?��?�� 권한
//				.build();
//
//		return new InMemoryUserDetailsManager(user); // 메모리에?�� �?�?
//	}

	// �? 150 ?��?���??�� ?���? ?��?�� ?��같이?�� ?��?�� (???��버전?��)
	// ?���?, ?���?�? 처리?��
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//�? 169?��?���??��?�� 로그?��
		// 3.x 버전 ?��?��?? lamda ?��?���? ?��?��?��?��?��
		http
		.formLogin((formlogin) -> formlogin
				.loginPage("/mem/login") //로그?�� ?��?���? url ?���? 주소
				.usernameParameter("username") //로그?�� ?�� ?��?��?�� ?��?��미터 ?���? ?��?��
				.defaultSuccessUrl("/") //로그?�� ?���? ?�� redirect
				.failureUrl("/mem/login") //로그?�� ?��?�� ?�� redirect
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
				.anyRequest().permitAll() //?�� ?��?�� ?��?��?���? ?��?? ?���??? 차단
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
//		firewall.setAllowUrlEncodedSlash(true); // ?��코딩?�� ?��?��?�� ?��?��
//		firewall.setAllowSemicolon(true); // ?��미콜�? ?��?��
//		firewall.setAllowUrlEncodedPercent(true); // ?��?��?�� ?��코딩 ?��?��
//		firewall.setAllowUrlEncodedDoubleSlash(true);
		return new DefaultHttpFirewall();
	}

	
}
