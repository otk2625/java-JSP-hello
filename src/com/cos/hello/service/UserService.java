package com.cos.hello.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.dao.UsersDao;
import com.cos.hello.dto.JoinDto;
import com.cos.hello.dto.LoginDto;
import com.cos.hello.model.Users;
import com.cos.hello.util.Script;

public class UserService {
	public void 회원가입(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		JoinDto joinDto = (JoinDto)req.getAttribute("dto"); //필터로 인한 객체전달 = 완전한 분리
		UsersDao usersDao = new UsersDao();
		
		int result = usersDao.insert(joinDto);

		if (result == 1) {
			// 3번 INSERT가 정상적으로 되었다면 index.jsp를 응답!
			resp.sendRedirect("auth/login.jsp");
		} else {
			resp.sendRedirect("auth/join.jsp");
		}
	}

	public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		LoginDto loginDto = (LoginDto)req.getAttribute("dto");
		UsersDao usersDao = new UsersDao();
		Users userEntity = usersDao.login(loginDto);

		if (userEntity == null) {
			Script.back(resp, "로그인 실패");
		} else {
			HttpSession session = req.getSession();
			session.setAttribute("sessionUser", userEntity);
			// 4번 index.jsp페이지로 이동
			// 한글 처리를 위해 resp객체를 건드린다.
			// MIME 타입, http header에 content-type
			Script.href(resp, "index.jsp", "로그인 성공");
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
