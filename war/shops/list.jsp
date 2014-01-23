<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageName" value="Shops" />
</jsp:include>

<c:choose>
	<c:when test="${ ! empty shops }">
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
	</c:when>
	<c:otherwise>
		<div class="row">
			<div class="col-xs-12 col-md-12">
				<strong><i>No shops yet!</i></strong>
			</div>
		</div>
	</c:otherwise>
</c:choose>

<jsp:include page="/common/footer.jsp" />
