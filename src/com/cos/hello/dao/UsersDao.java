package com.cos.hello.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.cos.hello.config.DBConn;
import com.cos.hello.model.Users;

public class UsersDao { //싱글톤 패턴으로 하자
	public int insert(Users user) {
		
		StringBuffer sb = new StringBuffer(); //String 전용 컬렉션(동기화);
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
			int result = pstmt.executeUpdate(); //변경된 행의 개수를 리턴
			
			return result; //1 또는 0
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("sql문제 : " + e.getMessage());
		}
		
		
		return 1;
	}
}
