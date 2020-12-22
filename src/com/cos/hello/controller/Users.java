package com.cos.hello.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Users {
	private int id; //primary key, Auto-increment
	private String username;
	private String password;
	private String email;
}
