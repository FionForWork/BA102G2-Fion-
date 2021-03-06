package com.album.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.album.model.AlbumService;
import com.album.model.AlbumVO;
import com.content.model.ContentService;
import com.content.model.ContentVO;

@MultipartConfig(fileSizeThreshold = 10 * 1024 * 1024, maxFileSize = 5 * 10 * 1021 * 1024, maxRequestSize = 5 * 5 * 10
		* 1024 * 1024)
public class AlbServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		System.out.println("@@@@@@@");
		
		/*********   查詢單本相簿內容       *********/
		if ("getOne_For_Display".equals(action)) {
			System.out.println("!!!!!!!!1");
			String alb_no = request.getParameter("alb_no");
			
			System.out.println(alb_no);
			request.setAttribute("alb_no", alb_no);
			String url ="/Front_end/Album/ListAllContents.jsp";
			request.getRequestDispatcher(url).forward(request, response);
			return;
		}
		
		/*********   刪除相簿與相簿內容       *********/
		if("delete_Album".equals(action)){
			AlbumService albSvc = new AlbumService();
			ContentService contSvc = new ContentService();
			
			String alb_no = request.getParameter("alb_no");
			System.out.println("alb_no"+alb_no);
			List<ContentVO> conts = contSvc.getAllByAlbNo(alb_no);
			for(ContentVO cont : conts){
				contSvc.deleteContent(cont.getCont_no());
			}
			
			albSvc.deleteAlbum(alb_no);
			
			/*********   回到相簿列表       *********/
			String url = "/Front_end/Album/ListAllAlbums.jsp";
			request.getRequestDispatcher(url).forward(request, response);
			return;
		}
		/*********   更新相簿       *********/
		if("update_Album".equals(action)){
			AlbumService albSvc = new AlbumService();
			String alb_no = request.getParameter("alb_no");
			String name = request.getParameter("name");
			String create_date = request.getParameter("create_date");
			
//			AlbumVO alb = albSvc.getOneAlbum(alb_no);
//			alb_no = 
			
		}
		/*********   建立相簿與相簿內容       *********/
		if ("create_Album".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			request.setAttribute("errorMsgs", errorMsgs);
			
			HttpSession session = request.getSession();
			String name = request.getParameter("name").trim();
			String mem_no = (String) session.getAttribute("mem_no");
			byte[] cover = null;
			Timestamp create_date = new Timestamp(System.currentTimeMillis());
			String alb_no = null;
			
			if (name.length() == 0) {
				name = new java.sql.Date(new java.util.Date().getTime()).toString();
			}

			ServletContext context = getServletContext();
			AlbumService albSvc = new AlbumService();
			ContentService contSvc = new ContentService();
			ContentVO cont = null;
			
			/*********  新增相簿    *********/
			AlbumVO alb = albSvc.addAlbum(mem_no, name, cover, create_date);
			
			/*********  新增相簿內容     *********/
			
			Collection<Part> parts = request.getParts();
			System.out.println("partsize::::"+parts.size());
			for (Part part : parts) {
				if (getFileNameFromPart(part) != null && part.getContentType() != null) {
					String filename = getFileNameFromPart(part);
					byte[] file = toByteArray(part.getInputStream());
					alb_no = alb.getAlb_no();
					if (isImgFile(context.getMimeType(filename))) {
						cont = contSvc.addContent(alb_no, new Timestamp(System.currentTimeMillis()), file, null);
					} else {
						contSvc.addContent(alb_no, new Timestamp(System.currentTimeMillis()), null, file);
					}
				}
			}
			
			/*********  修改相簿封面    *********/
			cover = cont.getImg();
			albSvc.updateAlbum(alb_no, mem_no, name, cover, create_date);
			
			/*********   導到相簿內容       *********/
			request.setAttribute("alb_no", alb_no);
			String url = "/Front_end/Album/ListAllContents.jsp";
			request.getRequestDispatcher(url).forward(request, response);
			return;
		}
	}

	private String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		System.out.println("header: " + header);
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}

	private byte[] toByteArray(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int length = 0;
		byte[] bytes = new byte[1024];
		while ((length = is.read(bytes)) != -1) {
			baos.write(bytes, 0, length);
		}
		baos.close();
		return baos.toByteArray();
	}

	private boolean isImgFile(String mimetype) {
		return mimetype != null && mimetype.startsWith("image");
	}

}
