package com.blog.configure;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	// ?Έμ¦λμ§? ??? ?¬?©?κ°? λ¦¬μ?€λ₯? ?μ²??  κ²½μ° Unauthorized ??¬ λ°μ?κ²λ
	// AuthenticationEntryPoint κ΅¬ν

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "?²??");
		//response.sendRedirect("/err/401");
	}

}
