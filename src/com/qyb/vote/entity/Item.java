package com.qyb.vote.entity;

//用户选择的选项类
public class Item {
	private int iid;   //编号
	private Subject subject;   //主题
	private Option option;  //用户确定选择的选项
	private String userName;    //用户
	private int sid;
	private int oid;
	private double num;    //每个主题总投票数
	private double onum;    //每个主题下的各个选项投票数
	
	public double getNum() {
		return num;
	}
	public void setNum(double num) {
		this.num = num;
	}
	public double getOnum() {
		return onum;
	}
	public void setOnum(double onum) {
		this.onum = onum;
	}
	public Item(int iid, int sid, int oid, String userName) {
		super();
		this.iid = iid;
		this.sid = sid;
		this.oid = oid;
		this.userName = userName;
	}
	public int getIid() {
		return iid;
	}
	public void setIid(int iid) {
		this.iid = iid;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public Option getOption() {
		return option;
	}
	public void setOption(Option option) {
		this.option = option;
	}
	public String getUserName() {
		return userName;
	}
	public void setUser(String userName) {
		this.userName = userName;
	}
	public Item() {
		super();
	}
	
}
