<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageName" value="Tags" />
</jsp:include>


<div class="row">
	<div class="col-xs-12 col-md-12">
		<h2>${name}</h2>
	</div>
</div>

<div class="row">
	<div class="col-xs-12 col-md-offset-3 col-md-6">
		<div class="alert alert-warning alert-dismissable">
			<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			<strong>Warning!</strong> This shop is not trusted, all content you see is user provided.
		</div>
	</div>
</div>

<div class="row">
	<div class="col-xs-12 col-md-12">
		<c:choose>
			<c:when test="${ ! empty products }">
				<h3 class="place-info">Products <span class="badge">${fn:length(products)}</span></h3>
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
			</c:when>
			<c:otherwise>
				<h4 class="place-info"><i>No posts refer to this shop!</i></h4>
			</c:otherwise>
		</c:choose>
	</div>
</div>

<div class="row">
	<div class="col-xs-12 col-md-12">
		<c:choose>
			<c:when test="${ ! empty posts }">
				<h3 class="place-info">Posts in this shop <span class="badge">${fn:length(posts)}</span></h3>
				<c:forEach var="post" items="${ posts }">
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
			</c:when>
			<c:otherwise>
				<h4 class="place-info"><i>No posts refer to this shop!</i></h4>
			</c:otherwise>
		</c:choose>
	</div>
</div>

<jsp:include page="/common/footer.jsp" />