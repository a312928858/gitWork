package com.qyb.vote.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.qyb.vote.dao.DBHelper;
import com.qyb.vote.entity.Item;
import com.qyb.vote.entity.Option;
import com.qyb.vote.entity.Subject;
import com.qyb.vote.entity.User;
import com.qyb.vote.service.VoteService;

public class VoteServiceImpl implements VoteService {
	private DBHelper db=new DBHelper();
	//用户注册
	public int addUser(User user) {
		String sql="insert into vusers values(?,?,?,1,1)";
		List<Object> params=new ArrayList<Object>();
		params.add(user.getUserId());
		params.add(user.getUserName());
		params.add(user.getPassword());
		return db.update(sql, params);
	}
	//用户登录
	public User login(User user) {
		String sql="select userId,userName,password from vusers where userId=? and password=?";
		List<Object> params=new ArrayList<Object>();
		params.add(user.getUserId());
		params.add(user.getPassword());
		List<User> list=db.find(sql, params, User.class);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	//添加投票主题
	public int addSubject(Subject subject) {
		String sql="insert into subject values(subject_sid.nextval,?,?,?,1)";
		List<Object> params=new ArrayList<Object>();
		params.add(subject.getUserName());
		params.add(subject.getTitle());
		params.add(subject.getType());
		return db.update(sql, params);
	}
	
	//添加主题下的选项
	public int addOption(Option option) {
		String sql="insert into options values(subject_oid.nextval,?,?,?)";
		List<Object> params=new ArrayList<Object>();
		params.add(option.getTitle());
		params.add(option.getName());
		params.add(option.getOrder());
		return db.update(sql, params);
	}
	 //查询所有主题信息
	public List<Subject> findVoteList() {
		String sql="select sid,type,userName,title from subject";
		List<Subject> list=db.find(sql,null,Subject.class);
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	
	//查询所有主题的投票人数
	public Subject findNumList(int sid) {
		String sql="select count(distinct userName) num from item where sid=?";
		List<Object> params=new ArrayList<Object>();
		params.add(sid);
		List<Subject> list=db.find(sql,params,Subject.class);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	 //查询主题对应的选项个数
	public Subject findNumOptions(String title) {
		String sql="select count(title) onum from options where title=?";
		List<Object> params=new ArrayList<Object>();
		params.add(title);
		List<Subject> list=db.find(sql,params,Subject.class);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	//根据主题模糊查询
	public List<Subject> findVote(String title) {
		String sql="select sid,type,userName,title from subject where title like ?";
		List<Object> params=new ArrayList<Object>();
		params.add("%"+title+"%");
		List<Subject> list=db.find(sql,params,Subject.class);
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	public Subject findVotes(String title) {
		String sql="select sid,type,userName,title from subject where title=?";
		List<Object> params=new ArrayList<Object>();
		params.add(title);
		List<Subject> list=db.find(sql,params,Subject.class);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	//根据主题查询选项
	public List<Option> findOptions(String title) {
		String sql="select * from options where title=?";
		List<Object> params=new ArrayList<Object>();
		params.add(title);
		List<Option> list=db.find(sql,params,Option.class);
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	
	//修改主题信息
	public int updateSubject(Subject subject) {
		String sql="update subject set title=?,type=? where sid=?";
		List<Object> params=new ArrayList<Object>();
		params.add(subject.getTitle());
		params.add(subject.getType());
		params.add(subject.getSid());
		return db.update(sql, params);
	}
	
	//修改主题下的选项信息
	public int uodateOption(Option option) {
		String sql="update options set title=?,name=? where oid=?";
		List<Object> params=new ArrayList<Object>();
		params.add(option.getTitle());
		params.add(option.getName());
		params.add(option.getOid());
		return db.update(sql, params);
	}
	
	//添加用户的投票
	public int addItem(Item item) {
		String sql="insert into item values(item_iid.nextval,?,?,?)";
		List<Object> params=new ArrayList<Object>();
		params.add(item.getSid());
		params.add(item.getOid());
		params.add(item.getUserName());
		return db.update(sql, params);
	}
	
	//根据sid查询该主题的总投票数
	public Item findVoteNum(int sid) {
		String sql="select count(*) num from item where sid=?";
		List<Object> params=new ArrayList<Object>();
		params.add(sid);
		List<Item> list=db.find(sql,params,Item.class);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	//查询该主题的选项投票数
	public Item findVoteOnum(int sid, int oid) {
		String sql="select count(*) onum from item where sid=? and oid=?";
		List<Object> params=new ArrayList<Object>();
		params.add(sid);
		params.add(oid);
		List<Item> list=db.find(sql,params,Item.class);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
