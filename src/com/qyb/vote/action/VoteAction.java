package com.qyb.vote.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.qyb.vote.entity.Item;
import com.qyb.vote.entity.Option;
import com.qyb.vote.entity.Subject;
import com.qyb.vote.entity.User;
import com.qyb.vote.service.VoteService;
import com.qyb.vote.service.impl.VoteServiceImpl;

public class VoteAction implements ServletRequestAware {
	private HttpServletRequest request;
	private VoteService vs;
	private User user;
	private List<Subject> subject;
	
	public VoteAction(){
		vs=new VoteServiceImpl();
	}
	
	//用户注册
	public String register(){
		String userId=request.getParameter("userId");
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		System.out.println("啦啦啦啦啦");
		int num=vs.addUser(new User(userId,userName,password));
		if(num>0){
			return "success"; 
		}
		return null;
	}
	
	//用户登录
	public String login(){
		String userId=request.getParameter("userId");
		String password=request.getParameter("password");
		user=vs.login(new User(userId,password));
		
		if(user!=null){
			request.getSession().setAttribute("user", user.getUserName());
			//调刷新投票列表的方法
			voteList();
			return "list";
		}
		return "fail";
	}
	
	//打开添加新投票页�?
	public String read(){
		return "read";
	}
	
	//返回投票列表的方�?
	public String list(){
		//调刷新投票列表的方法
		voteList();
		return "list";
	}
	
	//添加新投�?
	public String save(){
		String title=request.getParameter("title");
		int type=Integer.parseInt(request.getParameter("type"));
		String userName=(String) request.getSession().getAttribute("user");
		String[] options=request.getParameterValues("options");
		
		int num=vs.addSubject(new Subject(1,title,type,userName));
		int temp = 0;
		if(num>0){
			for(int i=0;i<options.length;i++){
				 temp=vs.addOption(new Option(1,options[i],(i+1),title));
				 if(temp==0){
					 System.out.println("添加主题下的选项失败....");
					 return "fail";
				 }
			}
			//调刷新投票列表的方法
			voteList();
			return "list";
		}else{
			System.out.println("添加投票主题失败.....");
			return "fail";
		}
	}
	
	//查询主题列表及对应的投票人数
	public void voteList(){
		subject=vs.findVoteList();
		if(subject!=null&&subject.size()>0){
			for(int i=0;i<subject.size();i++){
				Subject num=vs.findNumList(subject.get(i).getSid());
				subject.get(i).setNum(num.getNum());
			}
			for(int i=0;i<subject.size();i++){
				Subject onum=vs.findNumOptions(subject.get(i).getTitle());
				subject.get(i).setOnum(onum.getOnum());
			}
			request.getSession().setAttribute("list",subject);
		}else{
			System.out.println("查询主题列表失败....");
		}
	}
	
	//根据主题的模糊信息查�?
	public String search(){
		String title=request.getParameter("keywords");
		subject=vs.findVote(title);
		if(subject!=null&&subject.size()>0){
			for(int i=0;i<subject.size();i++){
				Subject num=vs.findNumList(subject.get(i).getSid());
				if(num==null || "".equals(num)){
					subject.get(i).setNum(0);
				}else{
					subject.get(i).setNum(num.getSid());
				}
			}
			for(int i=0;i<subject.size();i++){
				Subject onum=vs.findNumOptions(subject.get(i).getTitle());
				subject.get(i).setOnum(onum.getOnum());
			}
			request.getSession().setAttribute("list",subject);
		}else{
			request.getSession().setAttribute("list",null);
			System.out.println("查询主题列表失败....");
		}
		return "list";
	}
	
	//维护
	public String modify(){
		voteList();
		return "modify";
	}
	//打开维护页面
	public String update(){
		String title=request.getParameter("title");
		String userName=request.getParameter("userName");  //判断是否为管理员
		
		Subject sj=vs.findVotes(title);
		if(sj!=null){
			List<Option> options=vs.findOptions(title);
			if(options!=null&&options.size()>0){
				for(int i=0;i<options.size();i++){
					sj.addOption(options.get(i));
				}
			}
		}
		request.getSession().setAttribute("message",sj);
		return "update";
	}
	
	//对投票主题进行维�?
	public String xiugai(){
		int sid=Integer.parseInt(request.getParameter("sid"));
		String title=request.getParameter("title");
		int type=Integer.parseInt(request.getParameter("type"));
		String[] options=request.getParameterValues("options");
		String[] oids=request.getParameterValues("oids");
		
		int num1=vs.updateSubject(new Subject(title,type,sid));
		int num2=0;
		int temp=0;
		if(num1>0){
			for(int i=0;i<oids.length;i++){
				 num2=vs.uodateOption(new Option(title,options[i],Integer.parseInt(oids[i])));
				 if(num2==0){
					 System.out.println("维护主题下的选项失败....");
					 return "fail";
				 }
			}
			if(options.length>oids.length){
				for(int j=oids.length;j<options.length;j++){
					temp=vs.addOption(new Option(1,options[j],(j+1),title));
					 if(temp==0){
						 System.out.println("维护主题下的选项失败....");
						 return "fail";
					 }
				}
			}
		}else{
			System.out.println("修改投票信息失败...");
		}
		//调刷新投票列表的方法
		voteList();
		return "list";
	}
	
	//显示投票情况
	public String view(){
		String title=request.getParameter("title");
		int onum=Integer.parseInt(request.getParameter("onum"));
		int num=Integer.parseInt(request.getParameter("num"));
		
		Subject su =new Subject();
		su.setTitle(title);
		su.setOnum(onum);
		su.setNum(num);
		
		Subject se=vs.findVotes(title);
		
		//根据sid查询该主题的总投票数
		Item nums=vs.findVoteNum(se.getSid());
		double zoushu=nums.getNum();
		
		List<Option> options=vs.findOptions(title);
		if(options!=null&&options.size()>0){
			for(int i=0;i<options.size();i++){
				
				Item onums=vs.findVoteOnum(se.getSid(), options.get(i).getOid());
				double xuanshu=onums.getOnum();
				
				double pp=(double)Math.round((xuanshu/zoushu)*100)/100;   //百分比保�?位小�?
				
				String par=String.valueOf(pp*100);   //各个选项的百分比
				
				options.get(i).setOnum((int)xuanshu);
				options.get(i).setPar(par);
				
				su.addOption(options.get(i));
			}
		}
		request.getSession().setAttribute("view",su);
		return "view";
	}
	
	//打开投票窗口
	
	public String vote(){
		String title=request.getParameter("title");
		int onum=Integer.parseInt(request.getParameter("onum"));
		int num=Integer.parseInt(request.getParameter("num"));
		int type=Integer.parseInt(request.getParameter("type"));
		Subject su =new Subject();
		
		su.setTitle(title);
		su.setOnum(onum);
		su.setNum(num);
		su.setType(type);
		
		List<Option> options=vs.findOptions(title);
		if(options!=null&&options.size()>0){
			for(int i=0;i<options.size();i++){
				su.addOption(options.get(i));
			}
		}
		request.getSession().setAttribute("vote",su);
		return "vote";
	}
	
	//�?��投票
	public String savevote(){
		String title=request.getParameter("title");
		int onum=Integer.parseInt(request.getParameter("onum"));
		int num=Integer.parseInt(request.getParameter("num"));
		int type=Integer.parseInt(request.getParameter("type"));
		String userName=(String) request.getSession().getAttribute("user");
		String[] oids=request.getParameterValues("options");
		
		Subject sj=vs.findVotes(title);
		
		int temp=0;
		for(int i=0;i<oids.length;i++){
			temp=vs.addItem(new Item(1,sj.getSid(),Integer.parseInt(oids[i]),userName));
			 if(temp==0){
				 System.out.println("添加主题下的选项失败....");
				 return "fail";
			 }
		}
		//调刷新投票列表的方法
		voteList();
		return "savevote";
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
}
