package com.cos.hello.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("필터 걸림!");

		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		chain.doFilter(request, response); // 이거 안하면 아무것도 안뜸
	}

}
