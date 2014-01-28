<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageName" value="${f:h(shop.name)}" />
</jsp:include>

<script>
$(document).ready(function() {
	$('.carousel').carousel();
});
</script>

<c:if test="${sessionScope.shop != null && sessionScope.shop.name == shop.name}">
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
			<div id="post-form" class="col-xs-12 col-md-12" align="center">
				<a href="/advertises/add" class='btn btn-primary link'>New Advertise</a>
				<a href="/products/add" class='btn btn-primary link'>New Product</a>
			</div>
		</div>
	</div>
	<hr>
</c:if>
<div class="row">
	<c:if test="${shop.image != null}">
		<div class="col-xs-12 col-md-2" align="center">
			<c:set var="showUrl" value="/show?key=${f:h(shop.image.key)}&version=1" />
			<img class="thumbnail profile-thumbnail" src="${f:url(showUrl)}" />
		</div>
	</c:if>
	<div class="col-xs-12 col-md-3">
		<c:choose>
			<c:when test="${ sessionScope.shop.name == shop.name}">
				<h3>Your info:</h3>
			</c:when>
			<c:otherwise>
				<c:if test="${sessionScope.user != null && !isFollower}">
					<a href="/fellowship/new?shop=${shop.name}" class='btn btn-primary'>Follow</a>
				</c:if>
				<c:if test="${sessionScope.user != null && isFollower}">
					<a href="/fellowship/remove?shop=${shop.name}" class='btn btn-primary'>Unfollow</a>
				</c:if>
				<h3>${f:h(shop.name)}'s info:</h3>
			</c:otherwise>
		</c:choose>
		<p>Name: ${f:h(shop.name)}</p>
		<p>Email: <a href="mailto:${f:h(shop.email)}">${f:h(shop.email)}</a></p>
	</div>
</div>
<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-12" id="product-list">
		<c:choose>
			<c:when test="${ sessionScope.shop.name == shop.name}">
				<h3>Your products:</h3>
			</c:when>
			<c:otherwise>
				<h3>${f:h(shop.name)}'s products:</h3>
			</c:otherwise>
		</c:choose>
		<c:if test="${ empty shop.products }">
			<p><i>No products!</i></p>
		</c:if>
		<c:forEach var="product" items="${shop.products}">
			<div class="col-xs-12 col-sm-6 col-md-3">
				<div class="jumbotron">
					<p>
						<strong>Name:</strong>
						<c:choose>
							<c:when test="${ product.shop != null }">
								<a href="/shops/profile?name=${product.shop.name}">${product.name}</a>
							</c:when>
							<c:otherwise>
								${product.name}
							</c:otherwise>
						</c:choose>
					</p>
					<c:if test="${product.image != null}">
						<div align="center">
							<c:set var="showUrl" value="/show?key=${f:h(product.image.key)}&version=1" />
							<img class="thumbnail big-thumbnail" src="${f:url(showUrl)}" />
						</div>
					</c:if>
					<c:choose>
						<c:when test="${ ! empty product.tags  }">
							<p>
								<strong>Tags:</strong>
								<c:forEach var="tagAssociation" items="${product.tags}">
									<a href="/tags/search?name=${tagAssociation.tag.name}">${tagAssociation.tag.name}</a>
								</c:forEach>
							</p>
						</c:when>
					</c:choose>
				</div>
			</div>
		</c:forEach>
	</div>
</div>

<c:if test="${  sessionScope.shop.name == shop.name }">
	<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-12">
			<h3>Your advertises:</h3>
			<c:if test="${ empty shop.advertises }">
				<p><i>No advertises yet!</i></p>
			</c:if>
			<c:forEach var="advertise" items="${shop.advertises}" varStatus="externalStatus">
				<div class="col-xs-12 col-sm-6 col-md-3">
					<div class="jumbotron">
						<p><strong><fmt:formatDate value="${advertise.timestamp}" pattern="E d MMM yyyy"/></strong></p>
						<p>
							<strong>Shop:</strong>
							<a href="/shops/profile?name=${advertise.shop.name}">${advertise.shop.name}</a>
						</p>
						<div id="carousel${externalStatus.count}" class="carousel slide" style="width: 200px; margin: 0 auto">
							<div class="carousel-inner">
								<c:forEach var="advertisedProduct" items="${advertise.products}" varStatus="status">
									<c:choose>
										<c:when test="${status.count == 1}">
											<div class="item active">
										</c:when>
										<c:otherwise>
											<div class="item">
										</c:otherwise>
									</c:choose>
										<c:if test="${advertisedProduct.product.image != null}">
											<c:set var="showUrl" value="/show?key=${f:h(advertisedProduct.product.image.key)}&version=1" />
											<img src="${f:url(showUrl)}" class="thumbnail big-thumbnail" />
										</c:if>
										<div align="center">
											<p class="myCarouselCaption">${advertisedProduct.product.name}</p>
										</div>
									</div>
								</c:forEach>
							</div>
							<c:if test="${fn:length(advertise.products) > 1}">
								<a class="left carousel-control" href="#carousel${externalStatus.count}" data-slide="prev">
									<span class="glyphicon glyphicon-chevron-left"></span>
								</a>
								<a class="right carousel-control" href="#carousel${externalStatus.count}" data-slide="next">
									<span class="glyphicon glyphicon-chevron-right"></span>
								</a>
							</c:if>
						</div>
						<p class="description">${advertise.text}</p>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>

	<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-12">
			<h3>Your followers:</h3>
			<c:if test="${ empty shop.followers }">
				<p><i>No follower yet!</i></p>
			</c:if>
			<ul>
				<c:forEach var="fellowship" items="${shop.followers}">
					<li>
						<p><a href="/users/profile?username=${f:h(fellowship.user.username)}">
							${f:h(fellowship.user.name)} ${f:h(fellowship.user.surname)}
						</a></p>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</c:if>

<jsp:include page="/common/footer.jsp" />