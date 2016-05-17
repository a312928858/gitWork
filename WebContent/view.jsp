<%@ page  contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html>
<head>
<meta content="charset=utf-8">
<title>投票情况</title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
</head>
<body>
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
	<h2>查看投票</h2>
	<ul class="list">
		<li>
			<h4>${sessionScope.view.title }</h4>
			<p class="info">共有${sessionScope.view.onum } 个选项，已有 ${sessionScope.view.num } 个网友参与了投票。</p>
				<ol>
				   <c:forEach items="${sessionScope.view.options }" var="item">
						<li>
							<div class="rate">
								<div class="ratebg"><div class="percent" style="width:${item.par }%"></div></div>
								<p>${item.onum }票<span>(${item.par }%)</span></p>
							</div>
						</li>
				   </c:forEach>
				</ol>
				<div class="goback"><a href="Subject!list.action?entityId=103">返回投票列表</a></div>
		</li>
	</ul>
</div>
<div id="footer" class="wrap">
	源辰信息 &copy; 版权所有
</div>
</body>
</html>