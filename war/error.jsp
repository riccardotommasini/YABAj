<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageName" value="Error" />
</jsp:include>

<div class="row">
	<div class="col-xs-12 col-md-12" align="center">
		<strong>${messages[0]}</strong>
	</div>
</div>
<div class="row" id="error-list">
	<div class="col-xs-12 col-md-offset-4 col-md-4" align="center">
		<c:forEach  var="error" items="${messages}" begin="1" >
			<div class="alert alert-danger">${error}</div>
		</c:forEach>
	</div>
</div>
<div align="center"><a href="javascript:history.back()">Back</a></div>


<jsp:include page="/common/footer.jsp" />