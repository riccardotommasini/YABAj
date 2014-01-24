<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageName" value="Products" />
</jsp:include>

<c:choose>
	<c:when test="${ ! empty products }">
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
	</c:when>
	<c:otherwise>
		<div class="row">
			<div class="col-xs-12 col-md-12">
				<strong><i>No products yet!</i></strong>
			</div>
		</div>
	</c:otherwise>
</c:choose>

<jsp:include page="/common/footer.jsp" />