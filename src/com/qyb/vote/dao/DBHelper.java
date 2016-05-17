package com.qyb.vote.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.qyb.vote.utils.LogUtil;

public class DBHelper {
	private Connection con=null;
	private PreparedStatement pstmt=null;
	private ResultSet rs=null;
	/**
	 * 获取连接
	 * @return
	 */
	public Connection getConnection(){
		try {
			Context ctx=new InitialContext();
			DataSource ds=(DataSource) ctx.lookup("java:comp/env/jdbc/userInfo");
			con=ds.getConnection();
		} catch (Exception e) {
			LogUtil.log.error(e.toString());
			e.printStackTrace();
		}
		return con;
	}
	
	public void closeAll(Connection con,PreparedStatement pstmt,ResultSet rs){
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				LogUtil.log.error(e.toString());
				e.printStackTrace();
			}
		}
		
		
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				LogUtil.log.error(e.toString());
				e.printStackTrace();
			}
		}
		
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				LogUtil.log.error(e.toString());
				e.printStackTrace();
			}
		}
	}
	
	public void setValue(PreparedStatement pstmt,List<Object> params) throws SQLException{
		if(params!=null && params.size()>0){
			for(int i=0;i<params.size();i++){
				pstmt.setString(i+1, String.valueOf(params.get(i)));
			}
		}
		
	}
	
	
	public int update(String sql,List<Object> params){
		int result=0;
		try {
			con=this.getConnection();
			pstmt=con.prepareStatement(sql);
			
			
			if(params!=null && params.size()>0){
				for(int i=0;i<params.size();i++){
					pstmt.setString(i+1, String.valueOf(params.get(i)));
				}
			}
			
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			LogUtil.log.error(e.toString());
			e.printStackTrace();
		}finally{
			this.closeAll(con, pstmt, null);
		}
		return result;
	}
	
	public List<Map<String,Object>> find(String sql,List<Object> params){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=null;
		
		try {
			con=this.getConnection();
			pstmt=con.prepareStatement(sql);
			
			this.setValue(pstmt,params);
			
			rs=pstmt.executeQuery();
			
			ResultSetMetaData rsmd=rs.getMetaData();
			
			String[] colNames=new String[rsmd.getColumnCount()];
			
			for(int i=0;i<colNames.length;i++){
				colNames[i]=rsmd.getColumnName(i+1);
			}
			
			while(rs.next()){
				map=new HashMap<String,Object>();
				
				if(colNames!=null && colNames.length>0){
					for(String s:colNames){
						map.put(s,rs.getString(s));
					}
				}
				list.add(map);
			}
		} catch (SQLException e) {
			LogUtil.log.error(e.toString());
			e.printStackTrace();
		}finally{
			this.closeAll(con, pstmt, rs);
		}
		return list;
	}
	public double findPloymer(String sql,List<Object> params){
		double result=0;
		con=this.getConnection();
		
		try {
			pstmt=con.prepareStatement(sql);
			this.setValue(pstmt, params);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				result=rs.getDouble(1);
			}
		} catch (SQLException e) {
			LogUtil.log.error(e.toString());
			//e.printStackTrace();
		}finally{
			this.closeAll(con, pstmt, rs);
		}
		return result;
		
	}
	
	
	public List<Double> findPloyers(String sql,List<Object> params){
		List<Double> result=new ArrayList<Double>();
		con=this.getConnection();
		
		
		try {
			pstmt=con.prepareStatement(sql);
			this.setValue(pstmt, params);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				int count=rs.getMetaData().getColumnCount();
				for(int i=0;i<count;i++){
					result.add(rs.getDouble(i+1));
				}
			}
		} catch (SQLException e) {
			LogUtil.log.error(e.toString());
			e.printStackTrace();
		}finally{
			this.closeAll(con, pstmt, rs);
		}
		return result;
	}
	
	
	public boolean createOp(String sql){
		boolean b1=false;
		
		
		try {
			con=this.getConnection();
			pstmt=con.prepareStatement(sql);
			b1=pstmt.execute();
		} catch (SQLException e) {
			LogUtil.log.error(e.toString());
			//e.printStackTrace();
		}finally{
			this.closeAll(con, pstmt, rs);
		}
		return b1;
	}
	
	
	
public <T> List<T> find(String sql,List<Object> params,Class c){
		List<T> list=new ArrayList<T>();
		try {
			con=this.getConnection();
			pstmt=con.prepareStatement(sql);
			this.setValue(pstmt, params);
			rs=pstmt.executeQuery();
			
			//获取返回的结果集中的所有列的列名
			ResultSetMetaData rsmd=rs.getMetaData();//获取元数据，包含类的名称，类型等信息
			
			String[] colsName=new String[rsmd.getColumnCount()];
			
			for(int i=0;i<colsName.length;i++){
				
				colsName[i]=rsmd.getColumnName(i+1);
			}
			//获得给定的类的所有公有方法
			Method[] methods=c.getMethods();
//			for(Method m:methods){
//				System.out.println(m.getName());
//			}
			
			//set+列名 与给定的类中的方法进行忽略大小写比较，如果能匹配的上，则激活该方法将当前列的值注入到对象的对于属性中
			T t;
			String methodName=null;//方法名
			String colName=null;//列名
			String typeName=null;//参数类型名
			Object val=null;
			
			while(rs.next()){
				t=(T) c.newInstance();
				for(int i=0;i<colsName.length;i++){
					colName=colsName[i];
					val=rs.getObject(colName);
					
					for(Method md:methods){
						methodName=md.getName();
						if(("set"+colName).equalsIgnoreCase(methodName) && val!=null){
							//获取返回值得类型
							typeName=val.getClass().getName();
							if("java.math.BigDecimal".equals(typeName)){
								//激活当前方法注值
								md.invoke(t, rs.getInt(colName));
							}else{
								md.invoke(t, rs.getString(colName));
							}
						}
					}
				}
				list.add(t);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
}
