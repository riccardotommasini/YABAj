<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageName" value="Index" />
	<jsp:param name="script" value="geolocation" />
</jsp:include>

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
			<c:choose>
				<c:when test="${ sessionScope.user == null && sessionScope.shop == null  }">
					<p id="register"><a href="/signup">Register</a>
						or <a href="/login">Login</a> and start sharing your shopping experience!</p>
				</c:when>
				<c:otherwise>
					<div id="wait-geolocation">
						<span>You're about to be geolocated, please wait!</span>
						<img src="/img/ui-anim_basic_16x16.gif">
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>
<c:if test="${sessionScope.user != null && sessionScope.user.name == user.name}">
	<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-12">
			<h3>Recent advertises:</h3>
			<c:if test="${ empty advertises }">
				<p><i>No advertises yet!</i></p>
			</c:if>
			<c:forEach var="advertise" items="${advertises}">
				<div class="col-xs-12 col-sm-6 col-md-3">
					<div class="jumbotron">
						<p><strong><fmt:formatDate value="${advertise.timestamp}" pattern="E d MMM yyyy"/></strong></p>
						<p>
							<strong>Shop:</strong>
							<a href="/shops/profile?name=${advertise.shop.name}">${advertise.shop.name}</a>
						</p>
						<c:forEach var="advertisedProduct" items="${advertise.products}">
							<div align="center">
								${advertisedProduct.product.name}
								<c:if test="${advertisedProduct.product.image != null}">
									<c:set var="showUrl" value="/show?key=${f:h(advertisedProduct.product.image.key)}&version=1" />
									<img src="${f:url(showUrl)}" class="thumbnail big-thumbnail" />
								</c:if>
							</div>
						</c:forEach>
						<p>${advertise.text}</p>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</c:if>

<jsp:include page="/common/footer.jsp" />