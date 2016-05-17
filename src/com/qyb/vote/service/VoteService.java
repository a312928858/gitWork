package com.qyb.vote.service;

import java.util.List;

import com.qyb.vote.entity.Item;
import com.qyb.vote.entity.Option;
import com.qyb.vote.entity.Subject;
import com.qyb.vote.entity.User;

public interface VoteService {
	public int addUser(User user);    //用户注册
	
	public User login(User user);     //用户登录
	
	public int addSubject(Subject subject);    ////添加投票主题
	
	public int addOption(Option option);     //添加主题下的选项
	
	public List<Subject> findVoteList();          //查询所有主题信息
	
	public Subject findNumList(int sid);           //查询所有主题的投票人数
	
	public Subject findNumOptions(String title);   //查询主题对应的选项个数
	
	public List<Subject> findVote(String title);   //根据主题模糊查询
	
	public Subject findVotes(String title);
	
	public List<Option> findOptions(String title); //根据主题查询选项

	public int updateSubject(Subject subject);    //修改主题信息
	
	public int uodateOption(Option option);        //修改主题下的选项信息
	
	public int addItem(Item item);                //添加用户的投票
	
	public Item findVoteNum(int sid);      //根据sid查询该主题的总投票数
	
	public Item findVoteOnum(int sid,int oid);      //查询该主题的选项投票数
}
