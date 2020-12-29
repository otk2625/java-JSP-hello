package com.cos.hello.config;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.hello.util.ModifiableHttpServletRequest;

public class AttackFilter implements Filter{

	// 2번째 순서 또는 마지막 순서에 해야함
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("attackFilter");
		// post요청만 받아서 처리!
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String method = req.getMethod();
		
		System.out.println("Method : "+ method);
		if(method.equals("POST")) {
			//username에 '<>' 꺽쇠 들어오는 것을 방어
			//만약에 꺽쇠가 들어오면 전부 &lt; &gt;
			//다시 필터 타게 할 예정
			String username = request.getParameter("username");
			username = username.replaceAll("<", "&lt;")
			.replaceAll(">", "&gt;");
			
			System.out.println("username : " + username);
			ModifiableHttpServletRequest m = 
					new ModifiableHttpServletRequest((HttpServletRequest)req);
			m.setParameter("username", username);
			
			req = m;
		}
		
		
		chain.doFilter(req, response);
	}
	
}
