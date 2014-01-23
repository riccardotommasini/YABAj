<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/common/header.jsp">
<jsp:param name="pageName" value="Shops" />
</jsp:include>


<c:if test="${ ! empty users }">
	<div class="row">
		<div class="col-xs-12 col-md-12">
			<h3 class="title">Users <span class="badge">${fn:length(users)}</span></h3>
			<ul>
				<c:forEach var="user" items="${users}">
					<li>
						<p><a href="/users/profile?username=${f:h(user.username)}">
							${f:h(user.name)} ${f:h(user.surname)}
						</a></p>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</c:if>


<c:if test="${ ! empty places }">
	<div class="row">
		<div class="col-xs-12 col-md-12">
			<h3 class="title">Places <span class="badge">${fn:length(places)}</span></h3>
			<ul>
				<c:forEach var="place" items="${places}">
					<p><li>${f:h(place.name)}</li></p>
				</c:forEach>
			</ul>
		</div>
	</div>
</c:if>


<c:if test="${ ! empty products }">
	<div class="row">
		<div class="col-xs-12 col-md-12">
			<h3 class="title">Products <span class="badge">${fn:length(products)}</span></h3>
			<c:forEach var="product" items="${products}">
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
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</c:if>


<c:if test="${ ! empty shops }">
	<div class="row">
		<div class="col-xs-12 col-md-12">
			<h3 class="title">Shops <span class="badge">${fn:length(shops)}</span></h3>
			<ul>
				<c:forEach var="shop" items="${shops}">
					<li>
						<p><a href="/shops/profile?name=${f:h(shop.name)}">
							${f:h(shop.name)}
						</a></p>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</c:if>

<c:if test="${ ! empty posts }">
	<div class="row">
		<div class="col-xs-12 col-md-12">
			<h3 class="title">Posts <span class="badge">${fn:length(posts)}</span></h3>
			<c:forEach var="post" items="${posts}">
				<div class="col-xs-12 col-sm-6 col-md-3">
					<div class="jumbotron">
						<p>
							<strong>By:</strong>
							<a href="/users/profile?username=${post.user.username}">${post.user.name} ${post.user.surname}</a>
						</p>
						<p><strong><fmt:formatDate value="${post.timestamp}" pattern="E d MMM yyyy"/></strong></p>
						<p>
							<strong>Product:</strong> ${f:h(post.product.name)}
							<strong>at:</strong> ${f:h(post.productPrice)} &euro;
						</p>
						<c:if test="${post.image != null}">
							<div align="center">
								<c:set var="showUrl" value="/show?key=${f:h(post.image.key)}&version=1" />
								<img class="thumbnail big-thumbnail" src="${f:url(showUrl)}" />
							</div>
						</c:if>
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
	</div>
</c:if>

<c:if test="${empty users && empty places && empty products && empty shops && empty posts}">
	<div class="row">
		<div class="col-xs-12 col-md-12">
			<strong><i>No results!</i></strong>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12 col-md-12">
			<a href="javascript:history.back()">Back</a>
		</div>
	</div>
</c:if>

<jsp:include page="/common/footer.jsp" />
