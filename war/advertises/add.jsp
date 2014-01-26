<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageName" value="New Post" />
	<jsp:param name="script" value="suggest" />
	<jsp:param name="script" value="add" />
</jsp:include>

<div class="row">
	<div class="col-md-4 col-md-offset-4 ">
		<form name="addProduct" action="/advertises/new" method="POST" class="form-horizontal" role="form" >
			<div class="shop-product form-group">
				<div class="col-xs-12 col-md-12">
					<input id="product0" type="text" name="products" class="form-control" placeholder="Product"/>
				</div>
			</div>
			<div id="addable"></div>
			<div class="form-group">
				<div class="col-xs-12 col-md-12">
					<a href="#" id="add" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span></a>
					<a href="#" id="remove" class="btn btn-default"><span class="glyphicon glyphicon-minus"></span></a>
				</div>
			</div>
			<div class="form-group">
				<div class="col-xs-12 col-md-12">
					<textarea name="text" class="form-control" placeholder="Your Message" ></textarea>
				</div>
			</div>
			<div class="form-group" align="center">
				<div class="col-xs-12 col-md-12">
					<input type="submit" name="submit" value="Add Advertise" class="btn btn-primary" />
				</div>
			</div>
		</form>
	</div>
</div>

<jsp:include page="/common/footer.jsp" />
