<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageName" value="New Post" />
	<jsp:param name="script" value="uploadPreview" />
</jsp:include>


<div class="row">
	<div class="col-md-4 col-md-offset-4" align="center">
		<h3>Choose the account type</h3>
		<a href="/signup/users" id="users" class="btn btn-warning" />User</a>
		<a href="/signup/shops" id="shops" class="btn btn-warning" />Shop</a>
	</div>
</div>

<jsp:include page="/common/footer.jsp" />
