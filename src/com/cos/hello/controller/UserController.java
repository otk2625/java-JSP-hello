package com.cos.hello.controller;

import java.io.IOException;

import javax.servlet.ServletException;
//javax로 시작하는 패키지는 톰켓이 들고 있는 라이브러리
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController extends HttpServlet {

	// req와 res는 톰켓이 만들어준다. (클라이언트의 요청이 있을때마다)
	// req는 BufferedReader할 수 있는 ByteStream
	// res는 BufferedWriter할 수 있는 ByreStream

	// http://localhost:8000/hello/user?gubun=login
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("FrontController 실행됨");

//		String gubun = req.getRequestURL(); //StringBuffered
//		String gubun = req.getRequestURI(); //hello/front
		String gubun = req.getParameter("gubun");
		System.out.println(gubun);

		if (gubun.equals("login")) {
			resp.sendRedirect("auth/login.jsp"); // 한번더 request
		} else if (gubun.equals("join")) {
			resp.sendRedirect("auth/join.jsp"); // 한번더 request
		} else if (gubun.equals("selectOne")) {
			resp.sendRedirect("user/selectOne.jsp"); // 한번더 request
		} else if (gubun.equals("updateOne")) {
			resp.sendRedirect("user/updateOne.jsp"); // 한번더 request
		}
	}
}
