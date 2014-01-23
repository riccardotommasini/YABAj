<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageName" value="Recent Posts" />
</jsp:include>

<c:choose>
	<c:when test="${ ! empty posts }">
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
	</c:when>
	<c:otherwise>
		<div class="row">
			<div class="col-xs-12 col-md-12">
				<strong><i>No posts yet!</i></strong>
			</div>
		</div>
	</c:otherwise>
</c:choose>

<jsp:include page="/common/footer.jsp" />