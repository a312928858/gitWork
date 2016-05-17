<%@ page  contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html>
<head>
<meta content="charset=utf-8">
<title>维护投票</title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
<script type="text/javascript">
var isIE = !!document.all;
function AddOption()
{
	var voteoptions = document.getElementById("voteoptions");
	var _p = document.createElement("p");
	var _input = document.createElement("input");
	_input.type = "text";
	_input.className = "input-text";
	_input.setAttribute("name", "options");
	_p.appendChild(_input);
	var _a = document.createElement("a");
	_a.className = "del";
	_a.setAttribute("href", "javascript:;");
	if(isIE) {
		_a.attachEvent("onclick", DelOption);
	} else {
		_a.addEventListener("click", DelOption, false);
	}
	_a.appendChild(document.createTextNode("删除"));
	_p.appendChild(_a);
	voteoptions.appendChild(_p);
}
function DelOption(e)
{
	if(!e) e = window.event;
	var a = e.srcElement || e.target;
	var obj = a.parentNode;
	obj.parentNode.removeChild(obj);
}
</script>
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

<div id="voteManage" class="box">
	<h2>维护投票</h2>
	<div class="content">
	<form method="post" action="Subject!xiugai.action">
			<dl>
				<dt>投票内容：</dt>
				<dd>
				   <input type="hidden" name="sid" value="${sessionScope.message.sid }"/>
				   <input type="text" class="input-text" name="title"  value="${sessionScope.message.title }"/>
				</dd>
				<dt>投票类型：</dt>
				<dd>
		  		 	<c:if test="${sessionScope.message.type eq '1' }">
						<input type="radio" name="type" checked="checked" value="1" />单选
				   		<input type="radio" name="type"   value="2" />多选
					</c:if>
					
					<c:if test="${sessionScope.message.type eq '2' }">
						<input type="radio" name="type" value="1" />单选
				   		<input type="radio" name="type"  checked="checked" value="2" />多选
					</c:if>
				</dd>
				<dt>投票选项：</dt>
				
				<dd id="voteoptions">
					<c:forEach items="${sessionScope.message.options }" var="item">
						<p>
							<input type="hidden" class="input-text" name="oids" value="${item.oid }" />
							<input type="text" class="input-text" name="options" value="${item.name }" />
						</p>
					</c:forEach>
				</dd>
				
				
				<dt></dt>
				<dd class="button">
					<input type="image" src="images/button_submit.gif" />
					<a href="javascript:;" onclick="AddOption()">增加选项</a>
					<a href="Subject!modify.action">取消操作</a>
				</dd>
			</dl>
		</form>
	</div>
</div>
<div id="footer" class="wrap">
	源辰信息 &copy; 版权所有
</div>
</body>
</html>