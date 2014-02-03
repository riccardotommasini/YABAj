<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageName" value="Shops" />
</jsp:include>

<c:if test="${ ! empty products }">
	<div class="row">
		<div class="col-xs-12 col-md-12">
			<fmt:formatNumber var="numRows" value="${fn:length(products) div 4 + (fn:length(products) mod 4)}"  maxFractionDigits="0"/>
			<h3 class="title">Products <span class="badge">${fn:length(products)}</span></h3>
			<c:set var="index" value="1" />
			<c:forEach begin="1" end="${numRows}" var="i">
				<div class="row">
					<c:choose>
						<c:when test="${fn:length(products) <= 4}">
							<c:forEach var="product" items="${products}" varStatus="status">
								<div class="col-xs-12 col-sm-6 col-md-3">
									<div class="jumbotron">
										<p>
											${product.name}
										</p>
										<c:if test="${product.shop != null }">
											<p>
												<strong>Shop:</strong>
												<a href="/shops/profile?name=${product.shop.name}">${product.shop.name}</a>
											</p>
										</c:if>
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
							<c:forEach begin="${index}" end="${index + 3}" var="product" items="${products}" varStatus="status">
								<div class="col-xs-12 col-sm-6 col-md-3">
									<div class="jumbotron">
										<p>
											${product.name}
										</p>
										<c:if test="${product.shop != null }">
											<p>
												<strong>Shop:</strong>
												<a href="/shops/profile?name=${product.shop.name}">${product.shop.name}</a>
											</p>
										</c:if>
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
						</c:otherwise>
					</c:choose>
					<c:set var="index" value="${index + 4}" />
				</div>
			</c:forEach>
		</div>
	</div>
</c:if>

<c:if test="${ ! empty places }">
	<div class="row">
		<div class="col-xs-12 col-md-12">
			<h3 class="title">Shops <span class="badge">${fn:length(shops) + fn:length(places)}</span></h3>
			<ul>
				<c:forEach var="place" items="${places}">
					<li>
						<p>
						<c:choose>
							<c:when test="${place.shop != null}">
								<a href="/shops/profile?name=${f:h(place.shop.name)}">
									${f:h(place.shop.name)}<img src="/img/tick.png" class="tick" />
								</a>
							</c:when>
							<c:otherwise>
								<a href="/places/search?name=${f:h(place.name)}">
									${f:h(place.name)}
								</a>
							</c:otherwise>
						</c:choose>
						</p>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</c:if>

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

<c:if test="${ empty products && empty places && empty users }">
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
