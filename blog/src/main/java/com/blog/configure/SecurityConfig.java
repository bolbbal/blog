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
@EnableMethodSecurity(prePostEnabled = true) //security 6.x ?΄?
public class SecurityConfig {

	// λΉλ?λ²νΈ ??Έ?
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// ?¬?©? κ³μ  ??±
//	@Bean
//	public UserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder) {
//		UserDetails user = User.withUsername("admin") // ?¬?©? ?΄λ¦?
//				.password(passwordEncoder.encode("password")) // λΉλ?λ²νΈ ??Έ?
//				.roles("USER") // ?¬?©? κΆν
//				.build();
//
//		return new InMemoryUserDetailsManager(user); // λ©λͺ¨λ¦¬μ? κ΄?λ¦?
//	}

	// μ±? 150 ??΄μ§?? ?κΈ? ??° ?κ°μ΄? ?΄?΄ (???±λ²μ ?)
	// ?Έμ¦?, ?Έκ°?λ₯? μ²λ¦¬?¨
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//μ±? 169??΄μ§??? λ‘κ·Έ?Έ
		// 3.x λ²μ  ?΄??? lamda ??Όλ‘? ???΄?Ό?¨
		http
		.formLogin((formlogin) -> formlogin
				.loginPage("/mem/login") //λ‘κ·Έ?Έ ??΄μ§? url ?μ²? μ£Όμ
				.usernameParameter("username") //λ‘κ·Έ?Έ ? ?¬?©?  ??Όλ―Έν° ?΄λ¦? ?€? 
				.defaultSuccessUrl("/") //λ‘κ·Έ?Έ ?±κ³? ? redirect
				.failureUrl("/mem/login") //λ‘κ·Έ?Έ ?€?¨ ? redirect
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
				.anyRequest().permitAll() //?΄ ?? ?€? ?μ§? ??? ?μ²??? μ°¨λ¨
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
//		firewall.setAllowUrlEncodedSlash(true); // ?Έμ½λ©? ?¬?? ??©
//		firewall.setAllowSemicolon(true); // ?Έλ―Έμ½λ‘? ??©
//		firewall.setAllowUrlEncodedPercent(true); // ?Ό?Ό?Έ ?Έμ½λ© ??©
//		firewall.setAllowUrlEncodedDoubleSlash(true);
		return new DefaultHttpFirewall();
	}

	
}
