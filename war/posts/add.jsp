<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<jsp:include page="/common/header.jsp">
	<jsp:param name="pageName" value="New Post" />
	<jsp:param name="script" value="uploadPreview" />
	<jsp:param name="script" value="suggest" />
	<jsp:param name="tags" value="true" />
</jsp:include>

<style>
  .ui-autocomplete-loading {
    background: white url('/img/ui-anim_basic_16x16.gif') right center no-repeat;
  }
</style>

<div class="row">
	<div class="col-md-4 col-md-offset-4 ">
		<form name="addPost" action="/posts/new" method="POST" class="form-horizontal" role="form" enctype="multipart/form-data">
			<div class="form-group" id="preview" align="center">
				<div class="col-xs-12 col-md-12">
					<h3>New Post:</h3>
					<canvas id="canvas"></canvas>
					<img class="thumbnail" id="thumbnail" src="/img/thumbnail.png" />
				</div>
			</div>
			<div class="form-group" align="center" >
				<div class="col-xs-12 col-md-12">
					<div class="fileUpload btn btn-default">
					    <span>Product's Photo</span>
					    <input type="file" class="upload" capture="camera" accept="image/*" id="cameraInput" name="cameraInput" />
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-xs-12 col-md-12">
					<input type="text" name="product" id="input-product"  class="form-control" placeholder="Product Name"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-xs-12 col-md-12">
					<input type="text" name="tags" id="tag-list" class="tagit-hidden-field">
					<ul id="tags" class="tagit ui-widget ui-widget-content ui-corner-all"></ul>
				</div>
			</div>
			<div class="form-group">
				<div class="col-xs-12 col-md-12">
					<div class="input-group">
						<span class="input-group-addon">&euro;</span>
						<input type="text" name="price" id="input-price" class="form-control" placeholder="Product Price"/>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-xs-12 col-md-12">
					<input type="text" name="place" id="input-shop" class="form-control" placeholder="Shop or Place Name" value="${nearest}"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-xs-12 col-md-12">
					<textarea name="text" class="form-control" placeholder="Your Thought" ></textarea>
				</div>
			</div>
			<input type="hidden" name="latitude" value="${requestScope.latitude}"/>
			<input type="hidden" name="longitude" value="${requestScope.longitude}"/>
			<div class="form-group" align="center">
				<div class="col-xs-12 col-md-12">
					<input type="submit" name="submit" value="Post" class="btn btn-primary" />
				</div>
			</div>
		</form>
	</div>
</div>

<jsp:include page="/common/footer.jsp" />
