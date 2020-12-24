package com.cos.hello.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.dao.UsersDao;
import com.cos.hello.model.Users;
import com.cos.hello.util.Script;

public class UserService {
	public void 회원가입(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 데이터 원형 username = ssar & password = 1234 & email = ssar.nate.com

		// 1번 form의 input태그에 있는 3가지 값 username, password, email받기
		// getParameter함수는 get방식의 데이터와 post방식의 데이터를 다 받을 수 있음.
		// 단, post 방식에서는 데이터 타입이 x-www-form-urlencoded방식만 받을 수 있음
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");

		System.out.println("=================join START=================");
		System.out.println(username);
		System.out.println(password);
		System.out.println(email);
		System.out.println("==================join END==================");

		// 2번 DB에 연결해서 3가지 값을 INSERT하기
		Users user = Users.builder().username(username).password(password).email(email).build();

		UsersDao usersDao = new UsersDao();
		int result = usersDao.insert(user);

		if (result == 1) {
			// 3번 INSERT가 정상적으로 되었다면 index.jsp를 응답!
			resp.sendRedirect("auth/login.jsp");
		} else {
			resp.sendRedirect("auth/join.jsp");
		}
	}

	public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// SELECT id,username,email FROM users WHERE username=? and password=?
		// DAO함수명 : login() return값 : users 객체
		// 정상이면 세션에 Users오브젝트 담고 index.jsp, 비정상이면 login.jsp
		// 이 과정을 서비스에 담기(모듈화) - 응집도 증가, 결합도 하락

		// 1번 값 전달받기
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		System.out.println("=================login START=================");
		System.out.println(username);
		System.out.println(password);
		System.out.println("==================login END==================");
		// 2번 데이터베이스 값이 있는 select해서 확인

		Users users = Users.builder().username(username).password(password).build();

		UsersDao usersDao = new UsersDao();
		Users userEntity = usersDao.login(users);

		if (userEntity == null) {
			Script.back(resp, "로그인 실패");
		} else {
			HttpSession session = req.getSession();
			session.setAttribute("sessionUser", userEntity);
			// 4번 index.jsp페이지로 이동
			// 한글 처리를 위해 resp객체를 건드린다.
			// MIME 타입, http header에 content-type
			Script.href(resp, "index.jsp", "Login Sucuess");
		}

	}

	public void 유저정보보기(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// 인증이 필요한 페이지
		HttpSession session = req.getSession(); // 세션 받기
		Users user = (Users) session.getAttribute("sessionUser");

		UsersDao usersDao = new UsersDao();

		if (user != null) {
			System.out.println("유저정보보기 id : " + user.getId());
			Users userEntity = usersDao.selectById(user.getId());
			req.setAttribute("user", userEntity); // 키값 : user
			RequestDispatcher dis = req.getRequestDispatcher("user/selectOne.jsp");
			dis.forward(req, resp);
			System.out.println("유저정보보기 userEntity : " + userEntity.toString());
		} else {
			resp.sendRedirect("auth/login.jsp");
		}

	}

	public void 유저정보수정페이지(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// 인증이 필요한 페이지
		HttpSession session = req.getSession(); // 세션 받기
		Users user = (Users) session.getAttribute("sessionUser");
		UsersDao usersDao = new UsersDao();

		if (user != null) {
			Users userEntity = usersDao.selectById(user.getId());
			req.setAttribute("user", userEntity); // 키값 : user
			RequestDispatcher dis = req.getRequestDispatcher("user/updateOne.jsp");
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("auth/login.jsp");
		}

	}

	public void 유저정보수정(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// 인증이 필요한 페이지
		HttpSession session = req.getSession(); // 세션 받기
		Users user = (Users) session.getAttribute("sessionUser");
		UsersDao usersDao = new UsersDao();

		String password = req.getParameter("password");
		String email = req.getParameter("email");

		user.setPassword(password);
		user.setEmail(email);

		int result = usersDao.update(user);

		if (result == 1) {
//					RequestDispatcher dis = req.getRequestDispatcher("index.jsp");
//					dis.forward(req, resp);
			resp.sendRedirect("index.jsp");
		} else {
			resp.sendRedirect("user/selectOne.jsp");
		}

	}

	public void 회원삭제(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// 인증이 필요한 페이지
		HttpSession session = req.getSession(); // 세션 받기
		Users user = (Users) session.getAttribute("sessionUser");
		UsersDao usersDao = new UsersDao();

		int result = usersDao.delete(user);

		if (result == 1) {
			session.invalidate(); // 세션 무효화 로그아웃기능
			resp.sendRedirect("index.jsp");
		} else {
			resp.sendRedirect("user/selectOne.jsp");
		}

	}
}
