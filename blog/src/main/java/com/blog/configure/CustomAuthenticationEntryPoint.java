package com.blog.configure;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	// ?¸ì¦ë˜ì§? ?•Š?? ?‚¬?š©?ê°? ë¦¬ì†Œ?Š¤ë¥? ?š”ì²??•  ê²½ìš° Unauthorized ?—?Ÿ¬ ë°œìƒ?•˜ê²Œë”
	// AuthenticationEntryPoint êµ¬í˜„

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "?„²?…ˆ?……");
		//response.sendRedirect("/err/401");
	}

}
