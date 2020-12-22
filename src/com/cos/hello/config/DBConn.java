package com.cos.hello.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConn {
	public static Connection getInstance() {
		Connection conn = null;
		// 오라클 고정 접속시 사용되는 방식 : oci, thin방식 존재
		String url = "jdbc:mysql://localhost:3306/DB이름?serverTimezone=Asia/Seoul";
		String username = "ssar";
		String password = "bitc5600";
		
		//OracleDriver 클래스를 메모리에 로드
		try {
			//메모리의 클래스를 타입으로 찾아내서 로드
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url,username,password);
			System.out.println("DB연결 성공!!");
			return conn;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("DB연결 실패!!");
		return null;
	}
}
