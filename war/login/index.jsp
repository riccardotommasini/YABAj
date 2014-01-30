<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageName" value="Login" />
</jsp:include>

<div class="row">
	<div class="col-md-4 col-md-offset-4 ">
		<form name="addAccount" action="/login/login" method="POST" class="form-horizontal" role="form">
			<div class="form-group" align="center">
				<div class="col-xs-12 col-md-12">
					<a href='https://www.facebook.com/dialog/oauth?client_id=639186146139919&redirect_uri=http://localhost:8888/login/fblogin&scope=email,read_stream'>
						<img src="/img/fblogin.png" alt="Login">
					</a>
				</div>
			</div>
			<hr>
			<div class="form-group">
				<div class="col-xs-12 col-md-12">
					<input type="text" name="username" id="username" class="form-control" placeholder="Username"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-xs-12 col-md-12">
					<input type="password" name="password" id="password" class="form-control" placeholder="Password"/>
				</div>
			</div>
			<div class="form-group" align="center">
				<div class="col-xs-12 col-md-12">
					<input type="submit" name="submit" value="Login" class="btn btn-primary" />
				</div>
			</div>
		</form>
	</div>
</div>

<jsp:include page="/common/footer.jsp" />
