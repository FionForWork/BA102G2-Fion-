<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<jsp:useBean id="contSvc" scope="page"
	class="com.content.model.ContentService"></jsp:useBean>
<jsp:useBean id="albSvc" scope="page"
	class="com.album.model.AlbumService"></jsp:useBean>

<%
	String alb_no = (String) request.getAttribute("alb_no");
	// 	String alb_no = "0001";
	// 	session.setAttribute("alb_no","0001");
%>
<%@ include file="page/photo_header.file"%>

<div class="col-xs-12 col-sm-9 ">
	<!-- Photo Start Here -->
	<div class="jumbotron text-center">
		<div class="text-right">
			<button type="submit" class="btn btn-default" id="uploadPic">新增相片</button>
		</div>
		<!-- Modal -->
		<form action="<%=request.getContextPath()%>/content/content.do"
			method="post" enctype="multipart/form-data">
			<div class="modal fade" id="uploadModal" role="dialog">
				<div class="modal-dialog">

					<!-- Modal content-->
					<div class="modal-content">
						<div class="modal-header" style="padding: 35px 50px;">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4>
								<span class="glyphicon glyphicon-picture"></span> 上傳照片
							</h4>
						</div>
						<div class="modal-body" style="padding: 40px 50px;">

						
							<div class="form-group">
								<label for="upload"> 選擇照片</label> 
								<input type="file"
									class="form-control" name="uploadPic" id="upload" onchange="preview_images()"
									multiple>
							</div>
							<div id="showPanel"></div>
							<input type='submit' class="btn btn-info btn-block" value="新增">

						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-default pull-left"
								data-dismiss="modal">
								<span class="glyphicon glyphicon-remove"></span> Cancel
							</button>
						<input type='hidden' name='action' value='insert_Content'>
						<input type='hidden' name='alb_no' value='<%=alb_no %>'>
						</div>
					</div>

				</div>
			</div>
		</form>
		<!-- Modal -->
		<h2>${albSvc.getOneAlbum(alb_no).name}</h2>
	</div>


	<%
		System.out.println("!!!!~~~~~~~~");
	%>
	<c:forEach var="contVO" items="${contSvc.getAllByAlbNo(alb_no) }">
		<%
			System.out.println("!!!!!!!!!!");
		%>
		<div class="col-md-3 col-sm-4 col-xs-12">
			<a href="" data-caption="Image caption"> <img
				class="img-responsive img-thumbnail"
				src="/BA102G2/ShowPictureServletDAO?cont_no=${contVO.cont_no }" />
			</a>
		</div>
	</c:forEach>
	<br>
</div>

<%@ include file="page/album_footer.file"%>