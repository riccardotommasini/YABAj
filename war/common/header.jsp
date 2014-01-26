<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<!DOCTYPE HTML>

<html>
	<head>
		<title><%= request.getParameter("pageName")%></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="/img/favicon.ico">
		<script src="http://code.jquery.com/jquery-latest.min.js"></script>
		<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.min.js"></script>
		<%
		if(request.getParameter("tags") != null){
			out.println("<script src='/js/tag-it.min.js' type='text/javascript' charset='utf-8'></script>");
			out.println("<script src='/js/tags.js'></script>");
		}
		%>
		<link rel="stylesheet" href="/css/common.css">
		<link rel='stylesheet' href='http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.min.css'>
		<link rel="stylesheet" href="/css/bootstrap.min.css">
		<link rel="stylesheet" href="/css/bootstrap-theme.min.css">
		<%
		if(request.getParameter("tags") != null){
			out.println("<link href='/css/jquery.tagit.css' rel='stylesheet' type='text/css'>");
			out.println("<link href='/css/tagit.ui-zendesk.css' rel='stylesheet' type='text/css'>");
		}
		%>
		<script src="/js/bootstrap.min.js"></script>
		<%
		if(request.getParameterValues("script") != null){
			String[] values = request.getParameterValues("script");
			for(int i=0; i< values.length; i++){
				out.println("<script src='/js/" + values[i] + ".js'></script>");
			}
		}
		%>
		<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
			<script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
		<![endif]-->
	</head>

<body>
	<header>
		 <div class="navbar navbar-inverse navbar-static-top" role="navigation">
		  <div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/">YABA</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<c:choose>
						<c:when test="${fn:startsWith(requestScope['javax.servlet.forward.request_uri'], '/shops/list')}">
							<li class="active"><a href='/shops/list'>Shops</a></li>
						</c:when>
						<c:otherwise>
							<li><a href='/shops/list'>Shops</a></li>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${fn:startsWith(requestScope['javax.servlet.forward.request_uri'], '/products/list')}">
							<li class="active"><a href='/products/list'>Products</a></li>
						</c:when>
						<c:otherwise>
							<li><a href='/products/list'>Products</a></li>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${fn:startsWith(requestScope['javax.servlet.forward.request_uri'], '/posts/recent')}">
							<li class="active"><a href='/posts/recent'>Recent Posts</a></li>
						</c:when>
						<c:otherwise>
							<li><a href='/posts/recent'>Recent Posts</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<c:choose>
						<c:when test="${ sessionScope.user == null && sessionScope.shop == null  }">
							<c:choose>
								<c:when test="${fn:startsWith(requestScope['javax.servlet.forward.request_uri'], '/signup/')}">
									<li class="active"><a href='/signup'>Register</a></li>
								</c:when>
								<c:otherwise>
									<li><a href='/signup'>Register</a></li>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${requestScope['javax.servlet.forward.request_uri']  == '/login/'}">
									<li class="active"><a href='/login'>Login</a></li>
								</c:when>
								<c:otherwise>
									<li><a href='/login'>Login</a></li>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:if test="${ sessionScope.shop == null}">
								<c:choose>
									<c:when test="${username == sessionScope.user.username}">
										<li class="active"><a href="/users/profile?username=${sessionScope.user.username}">${sessionScope.user.name} ${sessionScope.user.surname}</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="/users/profile?username=${sessionScope.user.username}">${sessionScope.user.name} ${sessionScope.user.surname}</a></li>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${ sessionScope.user == null}">
								<c:choose>
									<c:when test="${name == sessionScope.shop.name}">
										<li class="active">
											<a href="/shops/profile?name=${sessionScope.shop.name}">${sessionScope.shop.name}</a>
										</li>
									</c:when>
									<c:otherwise>
										<li>
											<a href="/shops/profile?name=${sessionScope.shop.name}">${sessionScope.shop.name}</a>
										</li>
									</c:otherwise>
								</c:choose>
							</c:if>
							<li><a href="/logout">Logout</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		  </div>
		</div>
	</header>
	<div class="container">