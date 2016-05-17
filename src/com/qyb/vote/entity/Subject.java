package com.qyb.vote.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

//投票主题类
public class Subject implements Serializable{
	private static final long serialVersionUID = 1L;
	private static int TYPE_SINGLE=1;    //单选
	private static int TYPE_MULTIPLE=2;    //多选
	
	private int sid;   //编号
	private String title;  //标题
	private int type=1;   //类型
	private int version;  //是否有效
	private String userName;  //用户姓名
	private Set<Option> options=new HashSet<Option>();    ///选项的集合
	private int num;             //每个主题的投票人数
	private int onum;             //每个主题的选项数
	
	
	public int getOnum() {
		return onum;
	}
	public void setOnum(int onum) {
		this.onum = onum;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	public void addOption(Option option){
		this.options.add(option);
		option.setSubject(this);
	}
	
	public Set<Option> getOptions() {
		return options;
	}
	public void setOptions(Set<Option> options) {
		this.options = options;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + type;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subject other = (Subject) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	public Subject() {
		super();
	}
	public Subject(int sid, String title, int type, String userName) {
		super();
		this.sid = sid;
		this.title = title;
		this.type = type;
		this.userName = userName;
	}
	public Subject(String title, int type,int sid) {
		super();
		this.title = title;
		this.type = type;
		this.sid = sid;
	}
	
	
}
