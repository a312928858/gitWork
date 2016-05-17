<%@ page  contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html>
<head>
<meta content="charset=utf-8">
<title>参与投票</title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
<script type="text/javascript" src="jquery-1.11.3.js"></script>
</head>
<body>
<script type="text/javascript">
    	 $(function(){
 			$('#aixuexi').children('input[type=checkbox]').click(function(){
				
 				if($(this).is(':checked')){
					$(this).attr('checked',true).siblings().attr('checked',false);
				}else{
					$(this).attr('checked',false).siblings().attr('checked',false);
				}
  			});

		});
  </script>
	<div id="header" class="wrap">
	<img src="images/logo.gif" />
</div>
<div id="navbar" class="wrap">
	<div class="profile">
		您好，${sessionScope.user }
		<span class="return"><a href="Subject!list.action">返回列表</a></span>
		<span class="addnew"><a href="Subject!read.action">添加新投票</a></span>
		<span class="modify"><a href="Subject!modify.action">维护</a></span>
		
	</div>
	<div class="search">
		<form method="post" action="Subject!search.action">
			<input type="text" name="keywords" class="input-text" value=""/><input type="submit" name="submit" class="input-button" value="" />
		</form>
	</div>
</div>

<div id="vote" class="wrap">
	<h2>参与投票</h2>
	<ul class="list">
		<li>
			<c:if test="${sessionScope.vote.type eq '1' }">
				<h4>${sessionScope.vote.title }(单选)</h4>
			</c:if>
			
			<c:if test="${sessionScope.vote.type eq '2' }">
				<h4>${sessionScope.vote.title }(多选)</h4>
			</c:if>
			
			<p class="info">共有${sessionScope.vote.onum }个选项，已有${sessionScope.vote.num } 个网友参与了投票。</p>
			<form method="post" action="Vote!savevote.action">
			    <input type="hidden" name="title" value="${sessionScope.vote.title }"/>
			    <input type="hidden" name="onum" value="${sessionScope.vote.onum }"/>
			    <input type="hidden" name="num" value="${sessionScope.vote.num }"/>
			    <input type="hidden" name="type" value="${sessionScope.vote.type }"/>
			    
				<c:if test="${sessionScope.vote.type eq '1' }">
					<ol id="aixuexi">
						<c:forEach items="${sessionScope.vote.options }" var="item" varStatus="status">
								<input type="hidden" class="input-text" name="oids" value="${item.oid }" />
							${ status.index + 1}.&nbsp;&nbsp;<input type="checkbox" name="options"  value="${item.oid }"/> ${item.name }<br/><br/>
						</c:forEach>
					</ol>
				</c:if>
				<c:if test="${sessionScope.vote.type eq '2' }">
					<ol>
						<c:forEach items="${sessionScope.vote.options }" var="item">
							<p>
								<input type="hidden" class="input-text" name="oids" value="${item.oid }" />
								<li>
									<input type="checkbox" name="options"  value="${item.oid }"/>${item.name }
								</li>
							</p>
						</c:forEach>
					</ol>
				</c:if>
				<p class="voteView"><input type="image" src="images/button_vote.gif" /><a href="Vote!view.action?title=${sessionScope.vote.title }&onum=${sessionScope.vote.onum }&num=${sessionScope.vote.num }&type=${sessionScope.vote.type }"><img src="images/button_view.gif" /></a></p>
			</form>
		</li>
	</ul>
</div>

<div id="footer" class="wrap">
	源辰信息 &copy; 版权所有
</div>
</body>
</html>