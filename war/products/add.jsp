<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageName" value="New Post" />
	<jsp:param name="script" value="uploadPreview" />
</jsp:include>


<div class="row">
	<div class="col-md-4 col-md-offset-4 ">
		<form name="addProduct" action="/products/new" method="POST" class="form-horizontal" role="form" enctype="multipart/form-data">
			<div class="form-group" id="preview" align="center">
				<div class="col-xs-12 col-md-12">
					<canvas id="canvas"></canvas>
					<img class="thumbnail" id="thumbnail" src="/img/thumbnail.png" />
				</div>
			</div>
			<div class="form-group" align="center" >
				<div class="col-xs-12 col-md-12">
					<div class="fileUpload btn btn-default">
						<span>Product Image</span>
						<input type="file" class="upload" capture="camera" accept="image/*" id="cameraInput" name="cameraInput" />
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-xs-12 col-md-12">
					<input type="text" name="name" id="name" class="form-control" placeholder="Name"/>
				</div>
			</div>
			<div class="form-group" align="center">
				<div class="col-xs-12 col-md-12">
					<input type="submit" name="submit" value="Add Product" class="btn btn-primary" />
				</div>
			</div>
		</form>
	</div>
</div>

<jsp:include page="/common/footer.jsp" />
