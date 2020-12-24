package com.cos.hello.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cos.hello.config.DBConn;
import com.cos.hello.model.Users;

public class UsersDao { // 싱글톤 패턴으로 하자
	public int insert(Users user) {

		StringBuffer sb = new StringBuffer(); // String 전용 컬렉션(동기화);
		sb.append("INSERT INTO users(username, password, email) ");
		sb.append("VALUES(?,?,?)");
		String sql = sb.toString();
		Connection conn = DBConn.getInstance();
		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			System.out.println("sql완료");
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			int result = pstmt.executeUpdate(); // 변경된 행의 개수를 리턴

			return result; // 1 또는 0
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("sql문제 : " + e.getMessage());
		}

		return 1;
	}

	public Users login(Users users) {

		StringBuffer sb = new StringBuffer(); // String 전용 컬렉션(동기화);
		sb.append("SELECT id, username, email FROM users ");
		sb.append("WHERE username = ? AND password = ?");
		String sql = sb.toString();
		Connection conn = DBConn.getInstance();
		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			System.out.println("sql완료");
			pstmt.setString(1, users.getUsername());
			pstmt.setString(2, users.getPassword());

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				Users userEntity = Users.builder().id(rs.getInt("id")).username(rs.getString("username"))
						.email(rs.getString("email")).build();

				return userEntity;
			}
			// 1 또는 0
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("sql문제 : " + e.getMessage());
		}

		return null;
	}

	public Users selectById(int id) {
		StringBuffer sb = new StringBuffer(); // String 전용 컬렉션(동기화);
		sb.append("SELECT id, username, password, email FROM users ");
		sb.append("WHERE id = ?");
		String sql = sb.toString();
		Connection conn = DBConn.getInstance();
		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			System.out.println("sql완료");
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) { // 하나의 행만
				Users userEntity = Users.builder().id(rs.getInt("id")).username(rs.getString("username"))
						.password(rs.getString("password")).email(rs.getString("email")).build();

				System.out.println(userEntity.toString());
				return userEntity;
			}
			// 1 또는 0
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("sql문제 : " + e.getMessage());
		}

		return null;
	}

	public int update(Users user) {

		StringBuffer sb = new StringBuffer(); // String 전용 컬렉션(동기화);
		sb.append("UPDATE users SET password = ?, email = ? WHERE id = ?");
		String sql = sb.toString();
		Connection conn = DBConn.getInstance();
		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			System.out.println("sql완료");
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getEmail());
			pstmt.setInt(3, user.getId());

			int result = pstmt.executeUpdate(); // 변경된 행의 개수를 리턴

			return result; // 1 또는 0
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("sql문제 : " + e.getMessage());
		}

		return 0;
	}

	public int delete(Users user) {
		StringBuffer sb = new StringBuffer(); // String 전용 컬렉션(동기화);
		sb.append("DELETE FROM users WHERE id = ? ");
		String sql = sb.toString();
		Connection conn = DBConn.getInstance();
		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);
			System.out.println("sql완료");
			pstmt.setInt(1, user.getId());

			int result = pstmt.executeUpdate(); // 변경된 행의 개수를 리턴

			return result; // 1 또는 0
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("sql문제 : " + e.getMessage());
		}

		return 0;
	}
}
