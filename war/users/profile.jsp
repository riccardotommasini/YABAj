<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageName" value="${f:h(user.username)}" />
	<jsp:param name="script" value="geolocation" />
</jsp:include>

<c:if test="${sessionScope.user != null && sessionScope.user.name == user.name}">
	<div class="row">
		<div class="col-xs-12 col-md-offset-3 col-md-6">
			<form name="search" action="/search/" method="GET">
				<h3>Search:</h3>
				<div class="input-group">
					<input type="text" name="query" class="form-control" />
					<span class="input-group-btn">
						<button class="btn btn-default" type="submit">Search!</button>
					</span>
				</div>
			</form>
		</div>
		<div id="post-form" class="col-xs-12 col-md-12" align="center">
			<div class="input-group">
				<div id="wait-geolocation">
					<span>You're about to be geolocated, please wait!</span>
					<img src="/img/ui-anim_basic_16x16.gif">
				</div>
			</div>
		</div>
	</div>
	<hr>
</c:if>
<div class="row">
	<c:if test="${user.image != null}">
		<div class="col-xs-12 col-md-2" align="center">
			<c:set var="showUrl" value="/show?key=${f:h(user.image.key)}&version=1" />
			<img class="thumbnail profile-thumbnail" src="${f:url(showUrl)}" />
		</div>
	</c:if>
	<div class="col-xs-12 col-md-5">
		<p><strong>Name:</strong> ${f:h(user.name)}</p>
		<p><strong>Surname:</strong> ${f:h(user.surname)}</p>
		<p><strong>Username:</strong> ${f:h(user.username)}</p>
		<c:choose>
			<c:when test="${ sessionScope.user.username == user.username}">
				<p><strong>Email:</strong> ${f:h(user.email)}</p>
			</c:when>
		</c:choose>
	</div>
	<c:if test="${ ! empty user.following }">
		<div class="col-xs-12 col-md-offset-2 col-md-4">
			<h4 class="following">Following:</h4>
			<ul>
				<c:forEach var="fellowship" items="${user.following}">
					<li>
						<p><a href="/shops/profile?name=${fellowship.shop.name}">
							${fellowship.shop.name}
						</a></p>
					</li>
				</c:forEach>
			</ul>
		</div>
	</c:if>
</div>
<hr>
<div class="row">
	<div class="col-md-12">
		<c:choose>
			<c:when test="${ sessionScope.user.username == user.username}">
				<h3>Your posts:</h3>
				<c:if test="${ empty user.posts }">
					<p>
						<i>There aren't any posts yet! Go out and buy!</i>
					</p>
				</c:if>
			</c:when>
			<c:otherwise>
				<h3>${f:h(user.name)}'s posts:</h3>
				<c:if test="${ empty user.posts }">
					<p>
						<i>There aren't any posts yet!</i>
					</p>
				</c:if>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<div class="row">
	<c:forEach var="post" items="${ user.posts }">
		<div class="col-xs-12 col-sm-6 col-md-3">
			<div class="jumbotron">
				<p><strong><fmt:formatDate value="${post.timestamp}" pattern="E d MMM yyyy"/></strong></p>
				<p><strong>Product:</strong>
					<c:choose>
						<c:when test="${post.product.shop != null}">
							<a href="/shops/profile?name=${f:h(post.product.shop.name)}">
								${f:h(post.product.name)}</a>
						</c:when>
						<c:otherwise>
							${f:h(post.product.name)}
						</c:otherwise>
					</c:choose>
				</p>
				<c:if test="${post.image != null}">
					<div align="center">
						<c:set var="showUrl" value="/show?key=${f:h(post.image.key)}&version=1" />
						<img class="thumbnail big-thumbnail" src="${f:url(showUrl)}" />
					</div>
				</c:if>
				<p><strong>Price:</strong> ${f:h(post.productPrice)} &euro;</p>
				<c:choose>
					<c:when test="${f:h(post.place.shop.name != null)}">
						<p>
							<strong>At shop:</strong> <a
								href="/shops/profile?name=${f:h(post.place.shop.name)}">
								${f:h(post.place.shop.name)} </a>
						</p>
					</c:when>
					<c:otherwise>
						<p><strong>At place:</strong> ${f:h(post.place.name)}</p>
					</c:otherwise>
				</c:choose>
				<p><strong>With message:</strong> ${f:h(post.text)}</p>
			</div>
		</div>
	</c:forEach>
</div>

<jsp:include page="/common/footer.jsp" />