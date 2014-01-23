<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageName" value="${f:h(shop.name)}" />
</jsp:include>

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
				<h3>${f:h(shop.name)}'s info:</h3>
			</c:otherwise>
		</c:choose>
		<p>Name: ${f:h(shop.name)}</p>
		<p>Email: <a href="mailto:${f:h(shop.email)}">${f:h(shop.email)}</a></p>
	</div>
</div>
<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-12" id="product-list">
		<c:if test="${ sessionScope.user != null && !isFollower}">
			<p><a href="/fellowship/new?shop=${shop.name}" class='btn btn-primary'>Follow</a></p>
		</c:if>
		<c:if test="${ sessionScope.user != null && isFollower}">
			<p><a href="/fellowship/remove?shop=${shop.name}" class='btn btn-primary'>Unfollow</a></p>
		</c:if>
		<c:choose>
			<c:when test="${ sessionScope.shop.name == shop.name}">
				<h3>Your products:</h3>
				<p><a href="/products/add" class='btn btn-primary'>New Product</a></p>
			</c:when>
			<c:otherwise>
				<h3>${f:h(shop.name)}'s products:</h3>
			</c:otherwise>
		</c:choose>
		<c:if test="${ empty products }">
			<p><i>No products!</i></p>
		</c:if>
		<ul>
			<c:forEach var="product" items="${products}">
				<li>
					<div>
						<p>${f:h(product.name)}</p>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>
<c:if test="${  sessionScope.shop.name == shop.name }">
	<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-12" id="advertise-list">
			<h3>Your advertises:</h3>
			<p><a href="/advertises/add" class='btn btn-primary'>New Advertise</a></p>
			<c:if test="${ empty advertises }">
				<p><i>No advertises!</i></p>
			</c:if>
			<ul>
				<c:forEach var="advertise" items="${advertises}">
					<li>
						<div>
							<p>Product advertised: ${f:h(advertise.product.name)}</p>
							<p>On date ${f:h(advertise.timestamp)}</p>
							<p>With message: ${f:h(advertise.text)}</p>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-12" id="advertise-list">
			<h3>Your followers:</h3>
			<c:if test="${ empty followers }">
				<p><i>No follower yet!</i></p>
			</c:if>
			<ul>
				<c:forEach var="follower" items="${followers}">
					<li>
						<p><a href="/users/profile?username=${f:h(follower.user.username)}">
							${f:h(follower.user.name)} ${f:h(follower.user.surname)}
						</a></p>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</c:if>

<jsp:include page="/common/footer.jsp" />