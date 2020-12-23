package com.cos.hello.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//javax로 시작하는 패키지는 톰켓이 들고 있는 라이브러리
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.config.DBConn;
import com.cos.hello.model.Users;

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

		route(gubun, req, resp);

	}

	private void route(String gubun, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		if (gubun.equals("login")) {
			resp.sendRedirect("auth/login.jsp"); // 한번더 request
		} else if (gubun.equals("join")) {
			resp.sendRedirect("auth/join.jsp"); // 한번더 request
		} else if (gubun.equals("selectOne")) {
			// 인증이 필요한 페이지
			String result;
			HttpSession session = req.getSession();
			if(session.getAttribute("sessionUser") != null) {
				Users user = (Users)session.getAttribute("sessionUser");
				result = "인증되었습니다.";
				System.out.println(user);
			} else {
				result = "인증되지 않았습니다.";
			}
			req.setAttribute("result", result); //결과값 jsp에던지기 req에 저장하는게 좋음
			RequestDispatcher dis =
					req.getRequestDispatcher("user/selectOne.jsp");
			dis.forward(req, resp); //여기서 유지됨
		} else if (gubun.equals("updateOne")) {
			resp.sendRedirect("user/updateOne.jsp"); // 한번더 request
		} else if (gubun.equals("joinProc")) { // 회원가입 수행
			// 데이터 원형 username = ssar & password = 1234 & email = ssar.nate.com

			// 1번 form의 input태그에 있는 3가지 값 username, password, email받기
			// getParameter함수는 get방식의 데이터와 post방식의 데이터를 다 받을 수 있음.
			// 단, post 방식에서는 데이터 타입이 x-www-form-urlencoded방식만 받을 수 있음
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String email = req.getParameter("email");
			// 2번 DB에 연결해서 3가지 값을 INSERT하기
			// 생략

			// 3번 INSERT가 정상적으로 되었다면 index.jsp를 응답!
			System.out.println("=================join START=================");
			System.out.println(username);
			System.out.println(password);
			System.out.println(email);
			System.out.println("==================join END==================");
			String sql = "DELETE FROM users WHERE id = 2";
			Connection conn = DBConn.getInstance();
			PreparedStatement pstmt;
			
			try {
				pstmt = conn.prepareStatement(sql);
				System.out.println("sql완료");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			resp.sendRedirect("index.jsp");

			
		} else if(gubun.equals("loginProc")) {
			// 1번 값 전달받기
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String email = req.getParameter("email");
			System.out.println("=================login START=================");
			System.out.println(username);
			System.out.println(password);
			System.out.println("==================login END==================");
			// 2번 데이터베이스 값이 있는 select해서 확인
			
			
			// 생략
			Users user = Users.builder()
					.id(2)
					.username(username)
					.password(password)
					.email(email)
					.build();
			// 3번 
			HttpSession session = req.getSession();
			
			session.setAttribute("sessionUser", user);

			// 4번 index.jsp페이지로 이동
			resp.sendRedirect("index.jsp"); //바디에 담기는 데이터
		}
	}

}
