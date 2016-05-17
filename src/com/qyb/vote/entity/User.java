package com.qyb.vote.entity;

import java.io.Serializable;

//用户类
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	public static int USER_TYPE_ORDINARY=1;    //普通用户
	public static int USER_TYPE_ADMIN=2;       //管理员
	
	private String userId;   //用户编号
	private String userName;  //用户姓名
	private String password;  //密码
	private int version;   //用户是否有效
	private int status=1;   //用户角色  1.普通用户  2.	管理员
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	
	public boolean isAdministrator(){
		return (this.status==USER_TYPE_ADMIN);
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public User(String userId, String userName, String password) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
	}
	public User(String userId, String password) {
		super();
		this.userId = userId;
		this.password = password;
	}
	public User() {
		super();
	}
}
